package com.ashalmawia.foursquare

import android.app.Application
import android.content.Context
import com.ashalmawia.foursquare.dependency.ServiceLocator
import com.ashalmawia.foursquare.dependency.ServiceLocatorImpl

// open for testing
open class FourSquareApplication : Application() {

    // open for testing
    open val serviceLocator: ServiceLocator by lazy { ServiceLocatorImpl(this.applicationContext) }
}

val Context.serviceLocator: ServiceLocator
    get() = (applicationContext as FourSquareApplication).serviceLocator