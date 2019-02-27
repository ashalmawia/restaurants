package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.data.ErrorRepository
import com.ashalmawia.foursquare.data.MockRepository
import com.ashalmawia.foursquare.features.location.MockLocationProvider
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
class RestaurantsMapPresenterTest {

    private val locationProvider = MockLocationProvider()

    @Before
    fun before() {
        val thread = Schedulers.newThread()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { thread }
        RxJavaPlugins.setIoSchedulerHandler { thread }
    }

    @Test
    fun `restaurants are loaded and shown in the view`() {
        // given
        val list = listOf(
            restaurant(),
            restaurant(),
            restaurant()
        )

        val repository = MockRepository(list)
        val mockView = RestaurantsMapViewMock()

        val presenter = RestaurantsMapPresenterImpl(mockView, repository, locationProvider)

        // when
        presenter.start()
        Thread.sleep(200)       // having to wait because operation is asynchronous

        // then
        assertEquals(list, mockView.shownRestaurants)
        assertFalse(mockView.showErrorCalled)
    }

    @Test
    fun `error loading restaurants`() {
        // given
        val error = Exception("mock exception")
        val repository = ErrorRepository(error)
        val mockView = RestaurantsMapViewMock()
        val presenter = RestaurantsMapPresenterImpl(mockView, repository, locationProvider)

        // when
        presenter.start()
        Thread.sleep(200)       // having to wait because operation is asynchronous

        // then
        assertTrue(mockView.showErrorCalled)
        assertTrue(mockView.shownRestaurants.isEmpty())
    }
}