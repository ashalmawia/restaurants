package com.ashalmawia.foursquare.model

data class Details(
    val photo: String?,
    val description: String?,
    val address: String,
    val rating: Float?,
    val hours: String?,
    val priceCategory: Int?
)