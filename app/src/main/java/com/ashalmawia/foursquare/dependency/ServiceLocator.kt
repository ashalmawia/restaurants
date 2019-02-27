package com.ashalmawia.foursquare.dependency

import android.content.Context
import com.ashalmawia.foursquare.data.Repository
import com.ashalmawia.foursquare.data.network.NetworkRepository
import com.ashalmawia.foursquare.features.location.LocationProvider
import com.ashalmawia.foursquare.features.location.LocationProviderImpl
import com.ashalmawia.foursquare.image.ImageLoader
import com.ashalmawia.github_users.images.PicassoImageLoader

/**
 * As application grows, it should be better replaced with a dependency injection solution (Dagger)
 * or a third-party service locator with more features supported (e.g. Koin or Kodein)
 */
interface ServiceLocator {

    val repository: Repository

    val imageLoader: ImageLoader

    val locationProvider: LocationProvider

}

class ServiceLocatorImpl(private val context: Context) : ServiceLocator {

    override val repository: Repository by lazy { NetworkRepository() }

    override val imageLoader: ImageLoader by lazy { PicassoImageLoader() }

    override val locationProvider: LocationProvider by lazy { LocationProviderImpl(context) }
}