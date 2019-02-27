package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.Text

class RestaurantsMapViewMock : RestaurantsMapView {

    var shownLocation: Location? = null
    var shownRestaurants = mutableListOf<Restaurant>()
    var showErrorCalled = false

    override fun onShown() {
    }

    override fun updateLocation(location: Location) {
        shownLocation = location
    }

    override fun showRestaurants(restaurants: List<Restaurant>) {
        shownRestaurants.addAll(restaurants)
    }

    override fun showError(message: Text) {
        showErrorCalled = true
    }
}