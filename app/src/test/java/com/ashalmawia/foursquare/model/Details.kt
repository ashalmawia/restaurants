package com.ashalmawia.foursquare.model

fun details(
    photo: String? = "https://photo.url",
    description: String? = "Some description",
    address: String = "Keizergracht 356, Amsterdam",
    rating: Float? = 8.1f,
    hours: String? = "Open till 7pm",
    priceCategory: Int? = 3
) = Details(photo, description, address, rating, hours, priceCategory)

fun nullDetails() = details(photo = null, description = null, rating = null, hours = null, priceCategory = null)