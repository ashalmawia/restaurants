package com.ashalmawia.foursquare

import com.ashalmawia.foursquare.features.location.LocationProvider
import com.ashalmawia.foursquare.model.Location
import io.reactivex.Observable

class StubLocationProvider : LocationProvider {

    override val currentLocation: Observable<Location>
        get() = Observable.just(Location(52.3680, 4.9036))
}