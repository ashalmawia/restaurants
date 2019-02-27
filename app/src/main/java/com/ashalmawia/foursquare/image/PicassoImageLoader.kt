package com.ashalmawia.github_users.images

import android.widget.ImageView
import com.ashalmawia.foursquare.image.ImageLoader
import com.squareup.picasso.Picasso

class PicassoImageLoader : ImageLoader {

    override fun load(view: ImageView, url: String, placeholderId: Int) {
        Picasso.get()
            .load(url)
            .placeholder(placeholderId)
            .into(view)
    }
}