package com.ashalmawia.foursquare.data

import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import io.reactivex.Observable

class MockRepository(private val list: List<Restaurant>) : Repository {

    override fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>> {
        return Observable.just(list)
    }
}

class ErrorRepository(private val error: Throwable) : Repository {

    override fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>> {
        return Observable.error(error)
    }
}