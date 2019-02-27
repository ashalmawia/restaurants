package com.ashalmawia.foursquare.data.network.pojo

import com.ashalmawia.foursquare.model.Location
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponseBody(
    val response: SearchResponse
)

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val venues: List<VenuePojo>
)

@JsonClass(generateAdapter = true)
data class VenuePojo(
    val id: String,
    val name: String,
    val location: LocationPojo
)

@JsonClass(generateAdapter = true)
data class LocationPojo(
    val lat: Double,
    val lng: Double
) {
    fun toModel() = Location(lat, lng)
}