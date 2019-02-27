package com.ashalmawia.foursquare.model

data class Restaurant(val id: String, val name: String, val location: Location, val details: Details? = null)