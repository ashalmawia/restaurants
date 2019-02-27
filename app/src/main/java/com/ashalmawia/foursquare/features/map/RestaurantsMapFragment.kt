package com.ashalmawia.foursquare.features.map

import android.os.Bundle
import com.ashalmawia.foursquare.actionBar
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.navigator
import com.ashalmawia.foursquare.serviceLocator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

private const val KEY_CURRENT_LATITUDE = "latitude"
private const val KEY_CURRENT_LONGITUDE = "longitude"

class RestaurantsMapFragment : SupportMapFragment() {

    private var presenter: RestaurantsMapPresenter? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        getMapAsync { map -> initialize(map, bundle) }
    }

    private fun initialize(map: GoogleMap, bundle: Bundle?) {
        val view = RestaurantsMapViewImpl(map, actionBar, navigator)

        val serviceLocator = activity!!.serviceLocator

        val presenter = RestaurantsMapPresenterImpl(view, serviceLocator.repository, serviceLocator.locationProvider)
        this.presenter = presenter
        view.presenter = presenter

        val restoredLocation = bundle?.currentLocation
        presenter.start(restoredLocation)
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        saveCurrentLocation(bundle)
    }

    private fun saveCurrentLocation(bundle: Bundle) {
        presenter?.currentLocation?.let { bundle.putCurrentLocation(it) }
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.stop()
    }
}

private fun Bundle.putCurrentLocation(location: Location) {
    putDouble(KEY_CURRENT_LATITUDE, location.latitude)
    putDouble(KEY_CURRENT_LONGITUDE, location.longitude)
}

private val Bundle.currentLocation: Location?
    get() {
        return if (containsKey(KEY_CURRENT_LATITUDE) && containsKey(KEY_CURRENT_LONGITUDE)) {
            Location(getDouble(KEY_CURRENT_LATITUDE), getDouble(KEY_CURRENT_LONGITUDE))
        } else {
            null
        }
    }