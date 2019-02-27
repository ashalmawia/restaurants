package com.ashalmawia.foursquare.features.map

import androidx.appcompat.app.ActionBar
import com.ashalmawia.foursquare.R
import com.ashalmawia.foursquare.model.Restaurant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val ZOOM_STREETS = 15.0f

interface RestaurantsMapView {
    
    fun showRestaurants(location: LatLng, restaurants: List<Restaurant>)
}

class RestaurantsMapViewImpl(private val map: GoogleMap, private val actionBar: ActionBar) : RestaurantsMapView {

    override fun showRestaurants(location: LatLng, restaurants: List<Restaurant>) {
        updateTitle()
        updateLocation(location)
        displayRestaurants(restaurants)
    }

    private fun updateTitle() {
        actionBar.setTitle(R.string.restaurants_title)
    }

    private fun updateLocation(location: LatLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_STREETS))
    }

    private fun displayRestaurants(restaurants: List<Restaurant>) {
        restaurants.forEach {
            map.addMarker(
                MarkerOptions()
                    .position(it.location)
                    .title(it.name)
            )
        }
    }
}
