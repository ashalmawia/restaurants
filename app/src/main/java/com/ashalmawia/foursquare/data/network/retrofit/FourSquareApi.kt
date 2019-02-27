package com.ashalmawia.foursquare.data.network.retrofit

import com.ashalmawia.foursquare.data.network.pojo.DetailsResponse
import com.ashalmawia.foursquare.data.network.pojo.SearchResponseBody
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FourSquareApi {

    @GET("venues/search")
    fun searchRestaurants(
        @Query("ll") location: String,
        @Query("categoryId") categories: String
    ) : Observable<SearchResponseBody>

    @GET("venues/{id}")
    fun venueDetails(
        @Path("id") venueId: String
    ) : Observable<DetailsResponse.Body>
}