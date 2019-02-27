package com.ashalmawia.foursquare.features.map

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.ashalmawia.foursquare.Navigator
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

    fun onShown()

    fun updateLocation(location: Location)
    
    fun showRestaurants(restaurants: List<Restaurant>)

    fun showError(message: Text)
}

class RestaurantsMapViewImpl(
    private val map: GoogleMap,
    private val actionBar: ActionBar,
    private val navigator: Navigator
) : RestaurantsMapView {

    init {
        setUpMap()
    }

    private val context = actionBar.themedContext

    lateinit var presenter: RestaurantsMapPresenter

    /**
     * We check this permission on app's startup, 'cause this app doesn't make sense without that permission
     */
    @SuppressLint("MissingPermission")
    private fun setUpMap() {
        map.isMyLocationEnabled = true
        map.setOnCameraIdleListener {
            presenter.onNewLocation(map.cameraPosition.target.toLocation())
        }
    }

    override fun onShown() {
        updateTitle()
    }

    override fun updateLocation(location: Location) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location.toLanLng(), ZOOM_STREETS))
    }

    override fun showRestaurants(restaurants: List<Restaurant>) {
        displayRestaurants(restaurants)
    }

    private fun updateTitle() {
        actionBar.setTitle(R.string.restaurants_title)
    }

    private fun displayRestaurants(restaurants: List<Restaurant>) {
        map.clear()
        restaurants.forEach {
            val marker = map.addMarker(
                MarkerOptions()
                    .position(it.location.toLanLng())
                    .title(it.name)
            )
            marker.tag = it.id
        }
        map.setOnMarkerClickListener {
            onMarkerClicked(it.tag as String)
            true
        }
    }

    private fun onMarkerClicked(id: String) {
        navigator.openRestaurantDetails(id)
    }

    override fun showError(message: Text) {
        showToast(context, message, Toast.LENGTH_SHORT)
    }
}

private fun Location.toLanLng() = LatLng(latitude, longitude)
private fun LatLng.toLocation() = Location(latitude, longitude)