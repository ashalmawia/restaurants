package com.ashalmawia.foursquare.features.map

import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.ashalmawia.foursquare.R
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.Text
import com.ashalmawia.foursquare.util.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val ZOOM_STREETS = 15.0f

interface RestaurantsMapView {
    
    fun showRestaurants(location: Location, restaurants: List<Restaurant>)

    fun showError(message: Text)
}

class RestaurantsMapViewImpl(private val map: GoogleMap, private val actionBar: ActionBar) : RestaurantsMapView {

    private val context = actionBar.themedContext

    override fun showRestaurants(location: Location, restaurants: List<Restaurant>) {
        updateTitle()
        updateLocation(location)
        displayRestaurants(restaurants)
    }

    private fun updateTitle() {
        actionBar.setTitle(R.string.restaurants_title)
    }

    private fun updateLocation(location: Location) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location.toLanLng(), ZOOM_STREETS))
    }

    private fun displayRestaurants(restaurants: List<Restaurant>) {
        restaurants.forEach {
            map.addMarker(
                MarkerOptions()
                    .position(it.location.toLanLng())
                    .title(it.name)
            )
        }
    }

    override fun showError(message: Text) {
        showToast(context, message, Toast.LENGTH_SHORT)
    }
}

private fun Location.toLanLng() = LatLng(latitude, longitude)