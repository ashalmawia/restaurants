package com.ashalmawia.foursquare

import com.ashalmawia.foursquare.dependency.ServiceLocator
import com.ashalmawia.foursquare.dependency.ServiceLocatorImpl

class TestApplication : FourSquareApplication() {

    override val serviceLocator: ServiceLocator
        get() = mockServiceLocator ?: ServiceLocatorImpl(this)

    var mockServiceLocator: ServiceLocator? = null
}