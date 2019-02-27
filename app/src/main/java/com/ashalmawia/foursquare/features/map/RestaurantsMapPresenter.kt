package com.ashalmawia.foursquare.features.map

import com.ashalmawia.foursquare.R
import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.Text
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface RestaurantsMapPresenter {

    fun start()

    fun stop()

    fun onNewLocation(location: Location)
}

class RestaurantsMapPresenterImpl(
    private val view: RestaurantsMapView,
    private val repository: Repository
) : RestaurantsMapPresenter {

    private val subscriptions = CompositeDisposable()

    private var lastLocation: Location? = null

    override fun start() {
        view.onShown()

        val location = getCurrentLocation()
        view.updateLocation(location)
        loadRestaurants(location)
    }

    // stub
    private fun getCurrentLocation() = Location(52.3680, 4.9036)

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
        loadRestaurants(location)
    }
}