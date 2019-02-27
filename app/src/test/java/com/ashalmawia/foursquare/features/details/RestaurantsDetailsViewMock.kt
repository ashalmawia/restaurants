package com.ashalmawia.foursquare.features.details

import com.ashalmawia.foursquare.model.Restaurant

class RestaurantsDetailsViewMock : RestaurantDetailsView {

    var shownDetails: Restaurant? = null
    var showErrorCalled = false

    override fun showDetails(restaurant: Restaurant) {
        shownDetails = restaurant
    }

    override fun showError() {
        showErrorCalled = true
    }
}