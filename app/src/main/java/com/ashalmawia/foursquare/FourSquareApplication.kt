package com.ashalmawia.foursquare

import android.app.Application
import android.content.Context
import com.ashalmawia.foursquare.dependency.ServiceLocator
import com.ashalmawia.foursquare.dependency.ServiceLocatorImpl

class FourSquareApplication : Application() {

    val serviceLocator: ServiceLocator = ServiceLocatorImpl
}

val Context.serviceLocator: ServiceLocator
    get() = (applicationContext as FourSquareApplication).serviceLocator