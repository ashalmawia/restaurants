package com.ashalmawia.foursquare.data

import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import io.reactivex.Observable

class MockRepository(private val list: List<Restaurant> = listOf()) : Repository {

    override fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>> {
        return Observable.just(list)
    }

    override fun getRestaurantDetails(restaurantId: String): Observable<Restaurant> {
        val found = list.find { it.id == restaurantId }
        if (found != null) {
            return Observable.just(found)
        } else {
            return Observable.error(IllegalArgumentException())
        }
    }
}

class ErrorRepository(private val error: Throwable) : Repository {

    override fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>> {
        return Observable.error(error)
    }

    override fun getRestaurantDetails(restaurantId: String): Observable<Restaurant> {
        return Observable.error(error)
    }
}