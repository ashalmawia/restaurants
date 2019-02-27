package com.ashalmawia.foursquare.model

private var counter = 1

fun restaurant(name: String = "Restaurant ${counter++}", location: Location = location()) = Restaurant(name, location)