package com.ashalmawia.foursquare.features.location

import android.annotation.SuppressLint
import android.content.Context
import com.ashalmawia.foursquare.model.Location
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

interface LocationProvider {
    val currentLocation: Observable<Location>
}

class LocationProviderImpl(private val context: Context) : LocationProvider {

    override val currentLocation: Observable<Location> = Observable.create { emitter -> getLocation(emitter) }

    @SuppressLint("MissingPermission")
    private fun getLocation(emitter: ObservableEmitter<Location>) {
        val provider = LocationServices.getFusedLocationProviderClient(context)
        provider.lastLocation.addOnSuccessListener {
            emitter.onNext(it.toModel())
            emitter.onComplete()
        }
    }
}

private fun android.location.Location.toModel() = com.ashalmawia.foursquare.model.Location(latitude, longitude)