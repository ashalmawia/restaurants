package com.ashalmawia.foursquare.data.network.pojo

import com.ashalmawia.foursquare.model.Location
import com.squareup.moshi.JsonClass

interface DetailsResponse {

    @JsonClass(generateAdapter = true)
    data class Body(
        val response: Response
    )

    @JsonClass(generateAdapter = true)
    data class Response(
        val venue: VenueWithDetailsPojo
    )

    @JsonClass(generateAdapter = true)
    data class VenueWithDetailsPojo(
        val id: String,
        val name: String,
        val location: LocationExtendedPojo,
        val price: PricePojo?,
        val rating: Float?,
        val description: String?,
        val hours: HoursPojo?,
        val bestPhoto: PhotosPojo?
    )

    @JsonClass(generateAdapter = true)
    data class LocationExtendedPojo(
        val lat: Double,
        val lng: Double,
        val formattedAddress: Array<String>
    ) {
        fun toLocationModel() = Location(lat, lng)

        fun formattedAddress() = formattedAddress.joinToString(separator = "\n")

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LocationExtendedPojo

            if (lat != other.lat) return false
            if (lng != other.lng) return false
            if (!formattedAddress.contentEquals(other.formattedAddress)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = lat.hashCode()
            result = 31 * result + lng.hashCode()
            result = 31 * result + formattedAddress.contentHashCode()
            return result
        }
    }

    @JsonClass(generateAdapter = true)
    data class PricePojo(
        val tier: Int
    )

    @JsonClass(generateAdapter = true)
    data class HoursPojo(
        val status: String
    )

    @JsonClass(generateAdapter = true)
    data class PhotosPojo(
        val prefix: String,
        val suffix: String
    ) {
        fun toPhotoUrl() = "${prefix}width500$suffix"
    }
}