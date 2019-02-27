package com.ashalmawia.foursquare.features.map

import android.os.Bundle
import com.ashalmawia.foursquare.actionBar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class RestaurantsMapFragment : SupportMapFragment() {

    private lateinit var presenter: RestaurantsMapPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        getMapAsync { map -> initialize(map) }
    }

    private fun initialize(map: GoogleMap) {
        val view = RestaurantsMapViewImpl(map, actionBar)

        presenter = RestaurantsMapPresenterImpl(view)
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.stop()
    }
}