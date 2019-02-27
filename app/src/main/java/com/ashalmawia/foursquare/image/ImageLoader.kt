package com.ashalmawia.foursquare.image

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {

    fun load(view: ImageView, url: String, @DrawableRes placeholderId: Int)

    // could be made cancellable to be able to use it in lists, but as we don't need that for now leaving as is
}