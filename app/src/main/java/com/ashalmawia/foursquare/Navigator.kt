package com.ashalmawia.foursquare

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ashalmawia.foursquare.features.details.RestaurantDetailsFragment
import com.ashalmawia.foursquare.features.location.LocationPermissionFragment
import com.ashalmawia.foursquare.features.map.RestaurantsMapFragment

private const val TAG_LOCATION_PERMISSION = "location_permission"
private const val TAG_RESTAURANTS_MAP = "map"
private const val TAG_RESTAURANT_DETAILS = "details"

interface Navigator {

    fun openInitialScreen()

    fun openLocationPermissionChecker()

    fun openRestaurantsMap()

    fun openRestaurantDetails(restaurantId: String)

    fun openAppSettings()

    fun back()

    fun leave()
}

class NavigatorImpl(
    @IdRes private val containerId: Int,
    private val activity: Activity,
    private val fragmentManager: FragmentManager
) : Navigator {

    override fun openInitialScreen() {
        openLocationPermissionChecker()
    }

    override fun openLocationPermissionChecker() {
        val tag = TAG_LOCATION_PERMISSION
        val fragment = fragmentManager.findFragmentByTag(tag) ?: LocationPermissionFragment()
        moveToFragment(fragment, tag, false)
    }

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

    override fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${activity.packageName}"))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    override fun back() {
        fragmentManager.popBackStack()
    }

    override fun leave() {
        activity.finish()
    }
}