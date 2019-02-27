package com.ashalmawia.foursquare

import com.ashalmawia.foursquare.dependency.ServiceLocator
import com.ashalmawia.foursquare.dependency.ServiceLocatorImpl

class TestApplication : FourSquareApplication() {

    override val serviceLocator: ServiceLocator
        get() = mockServiceLocator ?: ServiceLocatorImpl

    var mockServiceLocator: ServiceLocator? = null
}