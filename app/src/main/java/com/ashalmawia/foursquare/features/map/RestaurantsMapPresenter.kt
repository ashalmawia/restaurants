package com.ashalmawia.foursquare.features.map

import android.util.Log
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
}

class RestaurantsMapPresenterImpl(
    private val view: RestaurantsMapView,
    private val repository: Repository
) : RestaurantsMapPresenter {

    private val subscriptions = CompositeDisposable()

    override fun start() {
        loadRestaurants(getCurrentLocation())
    }

    // stub
    private fun getCurrentLocation() = Location(52.3680, 4.9036)

    private fun loadRestaurants(location: Location) {
        val subscription = repository.getRestaurantsForLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onRestaurantsLoaded, this::onError)
        subscriptions.add(subscription)
    }

    private fun onRestaurantsLoaded(restaurants: List<Restaurant>) {
        view.showRestaurants(getCurrentLocation(), restaurants)
    }

    private fun onError(ignored: Throwable) {
        Log.d(this::class.java.simpleName, "failed to get restaurants", ignored)
        view.showError(Text.StringResource(R.string.error__restaurants_not_loaded))
    }

    override fun stop() {
        subscriptions.clear()
    }
}