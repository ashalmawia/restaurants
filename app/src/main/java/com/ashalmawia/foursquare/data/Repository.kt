package com.ashalmawia.foursquare.data

import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import io.reactivex.Observable

interface Repository {

    fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>>

    fun getRestaurantDetails(restaurantId: String): Observable<Restaurant>
}