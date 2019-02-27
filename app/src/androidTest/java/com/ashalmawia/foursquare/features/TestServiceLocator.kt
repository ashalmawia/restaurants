package com.ashalmawia.foursquare.features

import com.ashalmawia.foursquare.StubImageLoader
import com.ashalmawia.foursquare.StubLocationProvider
import com.ashalmawia.foursquare.StubRepository
import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.dependency.ServiceLocator
import com.ashalmawia.foursquare.features.location.LocationProvider
import com.ashalmawia.foursquare.image.ImageLoader
import com.ashalmawia.foursquare.model.Restaurant

class TestServiceLocator(private val restaurants: List<Restaurant>) : ServiceLocator {

    override val repository: Repository
        get() = StubRepository(restaurants)

    override val imageLoader: ImageLoader
        get() = StubImageLoader()

    override val locationProvider: LocationProvider
        get() = StubLocationProvider()
}