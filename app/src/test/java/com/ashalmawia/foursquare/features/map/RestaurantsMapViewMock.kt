package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.Text

class RestaurantsMapViewMock : RestaurantsMapView {

    var shownLocation: Location? = null
    var shownRestaurants = mutableListOf<Restaurant>()
    var showErrorCalled = false

    override fun showRestaurants(location: Location, restaurants: List<Restaurant>) {
        shownLocation = location
        shownRestaurants.addAll(restaurants)
    }

    override fun showError(message: Text) {
        showErrorCalled = true
    }
}