package com.ashalmawia.foursquare.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

var View.visible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }