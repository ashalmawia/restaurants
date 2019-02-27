package com.ashalmawia.foursquare.features.map

import android.os.Bundle
import com.ashalmawia.foursquare.actionBar
import com.ashalmawia.foursquare.navigator
import com.ashalmawia.foursquare.serviceLocator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class RestaurantsMapFragment : SupportMapFragment() {

    private lateinit var presenter: RestaurantsMapPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        getMapAsync { map -> initialize(map) }
    }

    private fun initialize(map: GoogleMap) {
        val view = RestaurantsMapViewImpl(map, actionBar, navigator)

        val serviceLocator = activity!!.serviceLocator

        presenter = RestaurantsMapPresenterImpl(view, serviceLocator.repository)
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.stop()
    }
}