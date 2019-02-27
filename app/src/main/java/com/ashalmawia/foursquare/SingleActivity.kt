package com.ashalmawia.foursquare

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SingleActivity : AppCompatActivity() {

    /**
     * Doesn't make sense to expose this as a part of an interface,
     * as either we have a single activity,
     * or in case of multiple activities they are expected to have different Navigator interfaces.
     */
    val navigator: Navigator by lazy { NavigatorImpl(R.id.container, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)

        navigator.openRestaurantsMap()
    }
}

/**
 * Doesn't make sense to expose this as a part of an interface,
 * as either we have a single activity,
 * or in case of multiple activities they are expected to have different Navigator interfaces.
 */
val Fragment.navigator: Navigator
    get() = (activity as SingleActivity).navigator

val Fragment.actionBar: ActionBar
    get() = (activity as AppCompatActivity).supportActionBar!!
