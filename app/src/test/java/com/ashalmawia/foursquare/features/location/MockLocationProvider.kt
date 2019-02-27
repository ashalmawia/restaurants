package com.ashalmawia.foursquare.features.location

import com.ashalmawia.foursquare.model.Location
import io.reactivex.Observable

class MockLocationProvider : LocationProvider {

    override val currentLocation: Observable<Location>
        get() = Observable.just(Location(23.32343, 73.32343))
}