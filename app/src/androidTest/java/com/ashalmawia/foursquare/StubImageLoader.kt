package com.ashalmawia.foursquare

import android.widget.ImageView
import com.ashalmawia.foursquare.image.ImageLoader

class StubImageLoader : ImageLoader {
    override fun load(view: ImageView, url: String, placeholderId: Int) {
        // ignore
    }
}