package com.ashalmawia.foursquare.model

private var counter = 1

fun restaurant(
    id: String = "id$counter",
    name: String = "Restaurant $counter",
    location: Location = location(),
    details: Details? = null
) : Restaurant {
    counter++
    return Restaurant(id, name, location, details)
}