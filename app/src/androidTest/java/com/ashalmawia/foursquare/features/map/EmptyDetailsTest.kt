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
class EmptyDetailsTest : BaseUiTest() {

    private val restaurantWithDetails = Restaurant(
        "id1", "Res 1", Location(52.3690, 4.9038), Details(
            null,
            null,
            "Keizersgracht 234, Amsterdam",
            null,
            null,
            null
        )
    )

    @Before
    override fun before() {
        applicationContext().mockServiceLocator = TestServiceLocator(listOf(restaurantWithDetails))
        super.before()
    }

    @Test
    fun testDetailsOpen() {
        // given
        val marker = device.findObject(By.descContains(restaurantWithDetails.name))
        marker.click()

        // when
        device.wait(Until.findObject(By.res(PACKAGE, "photo")), TIMEOUT)

        // then

        // photo is always shown, even if it's just a placeholder
        val photo = device.findObject(By.res(PACKAGE, "photo"))
        assertThat(photo, notNullValue())

        val description = device.findObject(By.res(PACKAGE, "labelDescription"))
        assertThat(description, nullValue())

        // address is always shown
        val address = device.findObject(By.res(PACKAGE, "labelAddress"))
        address.let {
            assertThat(it, notNullValue())
            assertThat(it.text, `is`(restaurantWithDetails.details!!.address))
        }

        val hours = device.findObject(By.res(PACKAGE, "labelHours"))
        assertThat(hours, nullValue())

        val rating = device.findObject(By.res(PACKAGE, "labelRating"))
        assertThat(rating, nullValue())

        val labelPriceCategory = device.findObject(By.res(PACKAGE, "labelPriceCategory"))
        assertThat(labelPriceCategory, nullValue())
    }

}