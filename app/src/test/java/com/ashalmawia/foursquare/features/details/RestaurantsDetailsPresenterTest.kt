package com.ashalmawia.foursquare.features.details

import com.ashalmawia.foursquare.MockNavigator
import com.ashalmawia.foursquare.data.ErrorRepository
import com.ashalmawia.foursquare.data.MockRepository
import com.ashalmawia.foursquare.model.details
import com.ashalmawia.foursquare.model.restaurant
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RestaurantsDetailsPresenterTest {

    private val navigator = MockNavigator()

    @Before
    fun before() {
        val thread = Schedulers.newThread()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { thread }
        RxJavaPlugins.setIoSchedulerHandler { thread }
    }

    @Test
    fun `restaurant details are loaded and shown in the view`() {
        // given
        val restaurant = restaurant(details = details())

        val repository = MockRepository(listOf(restaurant))
        val mockView = RestaurantsDetailsViewMock()

        val presenter = RestaurantDetailsPresenterImpl(mockView, repository, navigator)

        // when
        presenter.showRestaurantWithId(restaurant.id)
        Thread.sleep(200)       // having to wait because operation is asynchronous

        // then
        assertEquals(restaurant, mockView.shownDetails)
        assertFalse(mockView.showErrorCalled)
    }

    @Test
    fun `wrong restaurant id`() {
        // given
        val restaurant = restaurant(details = details())

        val repository = MockRepository(listOf(restaurant))
        val mockView = RestaurantsDetailsViewMock()

        val presenter = RestaurantDetailsPresenterImpl(mockView, repository, navigator)

        // when
        presenter.showRestaurantWithId("wrong id")
        Thread.sleep(200)       // having to wait because operation is asynchronous

        // then
        assertTrue(mockView.showErrorCalled)
        assertNull(mockView.shownDetails)
    }

    @Test
    fun `error loading restaurant details`() {
        // given
        val error = Exception("mock exception")
        val repository = ErrorRepository(error)
        val mockView = RestaurantsDetailsViewMock()
        val presenter = RestaurantDetailsPresenterImpl(mockView, repository, navigator)

        // when
        presenter.showRestaurantWithId("id")
        Thread.sleep(200)       // having to wait because operation is asynchronous

        // then
        assertTrue(mockView.showErrorCalled)
        assertNull(mockView.shownDetails)
    }
}