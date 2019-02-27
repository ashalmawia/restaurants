package com.ashalmawia.foursquare.features.map

import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.ashalmawia.foursquare.BaseUiTest
import com.ashalmawia.foursquare.PACKAGE
import com.ashalmawia.foursquare.TIMEOUT
import com.ashalmawia.foursquare.features.TestServiceLocator
import com.ashalmawia.foursquare.model.Details
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BasicMapTest : BaseUiTest() {

    private val restaurantWithDetails = Restaurant(
        "id1", "Res 1", Location(52.3690, 4.9038), Details(
            "photourl",
            "Restaurant description",
            "Keizersgracht 234, Amsterdam",
            8.3f,
            "Open till 7pm",
            2
        )
    )

    private val restaurants = listOf(
        restaurantWithDetails,
        Restaurant("id2", "Res 2", Location(52.3680, 4.9035)),
        Restaurant("id3", "Res 3", Location(52.3690, 4.9036))
    )

    @Before
    override fun before() {
        applicationContext().mockServiceLocator = TestServiceLocator(restaurants)
        super.before()
    }

    @Test
    fun testMarkersShown() {
        // when
        val markers = restaurants.map { device.findObject(By.descContains(it.name)) }

        // then
        markers.forEach { assertThat(it, notNullValue()) }
    }

    @Test
    fun testDetailsOpen() {
        // given
        val marker = device.findObject(By.descContains(restaurantWithDetails.name))
        marker.click()

        // when
        device.wait(Until.findObject(By.res(PACKAGE, "photo")), TIMEOUT)

        // then
        val photo = device.findObject(By.res(PACKAGE, "photo"))
        assertThat(photo, notNullValue())

        val description = device.findObject(By.res(PACKAGE, "labelDescription"))
        description.let {
            assertThat(it, notNullValue())
            assertThat(it.text, `is`(restaurantWithDetails.details!!.description))
        }

        val address = device.findObject(By.res(PACKAGE, "labelAddress"))
        address.let {
            assertThat(it, notNullValue())
            assertThat(it.text, `is`(restaurantWithDetails.details!!.address))
        }

        val hours = device.findObject(By.res(PACKAGE, "labelHours"))
        hours.let {
            assertThat(it, notNullValue())
            assertThat(it.text, `is`(restaurantWithDetails.details!!.hours))
        }

        val rating = device.findObject(By.res(PACKAGE, "labelRating"))
        rating.let {
            assertThat(it, notNullValue())
            assertThat(it.text, containsString(restaurantWithDetails.details!!.rating.toString()))
        }

        val labelPriceCategory = device.findObject(By.res(PACKAGE, "labelPriceCategory"))
        labelPriceCategory.let {
            assertThat(it, notNullValue())
            assertThat(it.text, `is`("$$"))
        }
    }

    @Test
    fun testOrientationChange() {
        device.setOrientationRight()
    }

}