package com.ashalmawia.foursquare

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ashalmawia.foursquare.features.details.RestaurantDetailsFragment
import com.ashalmawia.foursquare.features.map.RestaurantsMapFragment

private const val TAG_RESTAURANTS_MAP = "map"
private const val TAG_RESTAURANT_DETAILS = "details"

interface Navigator {

    fun openRestaurantsMap()

    fun openRestaurantDetails(restaurantId: String)

    fun back()
}

class NavigatorImpl(
    @IdRes private val containerId: Int,
    private val fragmentManager: FragmentManager
) : Navigator {

    override fun openRestaurantsMap() {
        val tag = TAG_RESTAURANTS_MAP
        val fragment = fragmentManager.findFragmentByTag(tag) ?: RestaurantsMapFragment()
        moveToFragment(fragment, tag, false)
    }

    override fun openRestaurantDetails(restaurantId: String) {
        val tag = TAG_RESTAURANT_DETAILS
        val fragment = fragmentManager.findFragmentByTag(tag) ?: RestaurantDetailsFragment()
        fragment.arguments = RestaurantDetailsFragment.arguments(restaurantId)
        moveToFragment(fragment, tag, true)
    }

    private fun moveToFragment(fragment: Fragment, tag: String, addToBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
            .replace(containerId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    override fun back() {
        fragmentManager.popBackStack()
    }
}