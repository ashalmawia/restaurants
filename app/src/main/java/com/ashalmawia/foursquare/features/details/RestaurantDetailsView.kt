package com.ashalmawia.foursquare.features.details

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.ashalmawia.foursquare.R
import com.ashalmawia.foursquare.image.ImageLoader
import com.ashalmawia.foursquare.model.Restaurant
import com.ashalmawia.foursquare.util.visible
import kotlinx.android.synthetic.main.fragment_restaurant_details.view.*

interface RestaurantDetailsView {

    fun showDetails(restaurant: Restaurant)

    fun showError()
}

class RestaurantDetailsViewImpl(
    private val root: View,
    private val appBar: ActionBar,
    private val imageLoader: ImageLoader
) : RestaurantDetailsView {

    override fun showDetails(restaurant: Restaurant) {
        val details = restaurant.details
        if (details == null) {
            showError()
            return
        }

        appBar.title = restaurant.name

        root.apply {
            val hasDescription = !details.description.isNullOrBlank()
            if (hasDescription) {
                labelDescription.text = details.description
            } else {
                labelDescription.visible = false
                labelDescriptionTitle.visible = false
            }

            labelAddress.text = details.address

            val hasRating = details.rating != null
            if (hasRating) {
                labelRating.text = context.getString(R.string.details__rating_placeholder, details.rating)
            } else {
                labelRating.visible = false
                labelRatingTitle.visible = false
            }

            val hasHours = !details.hours.isNullOrBlank()
            if (hasHours) {
                labelHours.text = details.hours
            } else {
                labelHours.visible = false
                labelHoursTitle.visible = false
            }

            val hasPriceCategory = details.priceCategory != null
            if (hasPriceCategory) {
                labelPriceCategory.text = details.priceCategory?.asReadablePriceCategory()
            } else {
                labelPriceCategory.visible = false
                labelPriceCategoryTitle.visible = false
            }

            details.photo?.let { imageLoader.load(photo, details.photo, R.drawable.placeholder) }

        }
    }

    override fun showError() {
        Toast.makeText(root.context, R.string.error__restaurant_details_empty, Toast.LENGTH_SHORT).show()
    }
}

private fun Int.asReadablePriceCategory() = "$".repeat(this)