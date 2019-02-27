package com.ashalmawia.foursquare.data.network

import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.data.network.retrofit.FourSquareApi
import com.ashalmawia.foursquare.data.network.retrofit.retrofit
import com.ashalmawia.foursquare.model.Details
import com.ashalmawia.foursquare.model.Location
import com.ashalmawia.foursquare.model.Restaurant
import io.reactivex.Observable

private val CATEGORIES = listOf(VenueCategory.FOOD)

class NetworkRepository : Repository {

    private val fourSquareApi: FourSquareApi

    init {
        val retrofit = retrofit()
        fourSquareApi = retrofit.create(FourSquareApi::class.java)
    }

    override fun getRestaurantsForLocation(location: Location): Observable<List<Restaurant>> {
        return fourSquareApi.searchRestaurants(location.toLL(), toApiFormat(CATEGORIES))
            .map { it.response.venues.map { venue -> Restaurant(venue.id, venue.name, venue.location.toModel()) } }
    }

    override fun getRestaurantDetails(restaurantId: String): Observable<Restaurant> {
        val updated = Restaurant(restaurantId, "Mock Restaurant", Location(23.234343, 23.23434), mockDetails())
        return Observable.just(updated)
    }

    private fun mockDetails() = Details(
        "https://fastly.4sqi.net/img/general/200x200/6036_Xv3VOJm0A8HMF8EbQWdKPXIce7LxcvXOMt4_nW5gDhU.jpg",
        "The best restaurant of European cuisine ever",
        "Keizergracht 35A, Amsterdam",
        8,
        "9:00 - 18:00",
        3
    )
}

private fun Location.toLL() = "$latitude,$longitude"

private fun toApiFormat(categories: List<VenueCategory>) = categories.joinToString { it.id }