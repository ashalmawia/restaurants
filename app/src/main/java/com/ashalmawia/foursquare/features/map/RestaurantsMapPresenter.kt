package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.R
import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.features.location.LocationProvider
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.Text
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface RestaurantsMapPresenter {

    val currentLocation: Location

    fun start(location: Location?)

    fun stop()

    fun onNewLocation(location: Location)
}

class RestaurantsMapPresenterImpl(
    private val view: RestaurantsMapView,
    private val repository: Repository,
    private val locationProvider: LocationProvider
) : RestaurantsMapPresenter {

    private val subscriptions = CompositeDisposable()

    private var lastLocation: Location? = null

    override val currentLocation: Location
        get() = view.currentLocation

    override fun start(location: Location?) {
        view.onShown()
        if (location == null) {
            getCurrentLocation()
        } else {
            onCurrentLocation(location)
        }
    }

    private fun getCurrentLocation() {
        val subscription = locationProvider.currentLocation
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onCurrentLocation, this::onFailedToLoadLocation)
        subscriptions.add(subscription)
    }

    private fun onCurrentLocation(location: Location) {
        view.updateLocation(location)
        loadRestaurants(location)
    }

    private fun onFailedToLoadLocation(throwable: Throwable) {
        view.showError(Text.StringResource(R.string.error__current_location_not_loaded))
    }

    private fun loadRestaurants(location: Location) {
        if (lastLocation == location) {
            // location didn't change, skip
            return
        }

        val subscription = repository.getRestaurantsForLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onRestaurantsLoaded, this::onError)
        subscriptions.add(subscription)
        lastLocation = location
    }

    private fun onRestaurantsLoaded(restaurants: List<Restaurant>) {
        view.showRestaurants(restaurants)
    }

    private fun onError(ignored: Throwable) {
        view.showError(Text.StringResource(R.string.error__restaurants_not_loaded))
    }

    override fun stop() {
        subscriptions.clear()
    }

    override fun onNewLocation(location: Location) {
        subscriptions.clear()
        loadRestaurants(location)
    }
}