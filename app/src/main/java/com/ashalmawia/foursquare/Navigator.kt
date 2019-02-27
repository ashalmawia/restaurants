package com.ashalmawia.foursquare

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.ashalmawia.foursquare.features.map.RestaurantsMapFragment

private const val TAG_RESTAURANTS_MAP = "map"

interface Navigator {

    fun openRestaurantsMap()

    fun back()
}

class NavigatorImpl(
    @IdRes private val containerId: Int,
    private val fragmentManager: FragmentManager
) : Navigator {

    override fun openRestaurantsMap() {
        val tag = TAG_RESTAURANTS_MAP

        val fragment = fragmentManager.findFragmentByTag(tag) ?: RestaurantsMapFragment()
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    override fun back() {
        fragmentManager.popBackStack()
    }
}