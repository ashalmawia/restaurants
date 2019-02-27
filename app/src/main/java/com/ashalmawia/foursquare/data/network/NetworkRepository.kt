package com.ashalmawia.foursquare.data.network

import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.data.network.pojo.DetailsResponse
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
        return fourSquareApi.venueDetails(restaurantId)
            .map(::detailsResponseToModel)
    }
}

private fun Location.toLL() = "$latitude,$longitude"

private fun toApiFormat(categories: List<VenueCategory>) = categories.joinToString { it.id }

private fun detailsResponseToModel(response: DetailsResponse.Body) : Restaurant {
    val venue = response.response.venue
    val details = Details(
        venue.bestPhoto?.toPhotoUrl(),
        venue.description,
        venue.location.formattedAddress(),
        venue.rating,
        venue.hours?.status,
        venue.price?.tier
    )
    return Restaurant(venue.id, venue.name, venue.location.toLocationModel(), details)
}