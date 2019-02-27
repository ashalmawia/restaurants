package com.ashalmawia.foursquare

import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import io.reactivex.Observable

class StubRepository(private val list: List<Restaurant>) : Repository {

    override fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>> {
        return Observable.just(list)
    }

    override fun getRestaurantDetails(restaurantId: String): Observable<Restaurant> {
        val found = list.find { it.id == restaurantId }
        return if (found != null) {
            Observable.just(found)
        } else {
            Observable.error(IllegalArgumentException())
        }
    }
}