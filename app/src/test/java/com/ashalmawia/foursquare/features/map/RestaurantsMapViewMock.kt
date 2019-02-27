package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.Text

class RestaurantsMapViewMock : RestaurantsMapView {

    var _shownLocation: Location? = null
    var _shownRestaurants = mutableListOf<Restaurant>()
    var _showErrorCalled = false

    override fun showRestaurants(location: Location, restaurants: List<Restaurant>) {
        _shownLocation = location
        _shownRestaurants.addAll(restaurants)
    }

    override fun showError(message: Text) {
        _showErrorCalled = true
    }
}