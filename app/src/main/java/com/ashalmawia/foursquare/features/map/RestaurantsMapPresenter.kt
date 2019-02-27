package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.model.Restaurant
import com.google.android.gms.maps.model.LatLng

interface RestaurantsMapPresenter {

    fun start()

    fun stop()
}

class RestaurantsMapPresenterImpl(private val view: RestaurantsMapView) : RestaurantsMapPresenter {

    override fun start() {
        view.showRestaurants(getCurrentLocation(), getRestaurants())
    }

    // stub
    private fun getCurrentLocation() = LatLng(52.3680, 4.9036)

    // stub
    private fun getRestaurants() = listOf<Restaurant>()

    override fun stop() {
    }
}