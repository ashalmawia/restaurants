package com.ashalmawia.foursquare.features.details

import android.util.Log
import com.ashalmawia.foursquare.Navigator
import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.model.Restaurant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface RestaurantDetailsPresenter {

    fun showRestaurantWithId(id: String)

    fun noRestaurantId()

    fun stop()
}

class RestaurantDetailsPresenterImpl(
    private val view: RestaurantDetailsView,
    private val repository: Repository,
    private val navigator: Navigator
) : RestaurantDetailsPresenter {

    private val subscriptions = CompositeDisposable()

    override fun showRestaurantWithId(id: String) {
        loadRestaurantDetails(id)
    }

    private fun loadRestaurantDetails(id: String) {
        val subscription = repository.getRestaurantDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onDetailsLoaded, { error ->
                Log.e("FOURSQUARE", "error", error)
                onError() })
        subscriptions.add(subscription)
    }

    private fun onDetailsLoaded(restaurant: Restaurant) {
        view.showDetails(restaurant)
    }

    override fun noRestaurantId() {
        onError()
    }

    private fun onError() {
        view.showError()
        navigator.back()
    }

    override fun stop() {
        subscriptions.clear()
    }
}