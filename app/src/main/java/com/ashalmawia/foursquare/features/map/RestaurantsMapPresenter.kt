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
    private fun getRestaurants() = listOf(
        Restaurant("Starbucks", LatLng(52.36607678472145, 4.897430803910262)),
        Restaurant("Manneken Pis", LatLng(52.37570751971382, 4.896236799445381)),
        Restaurant("Bocca", LatLng(52.36443129817435, 4.886863899263005)),
        Restaurant("Oriental City", LatLng(52.371911733724474, 4.895993555789719))
    )

    override fun stop() {
    }
}