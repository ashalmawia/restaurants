package com.ashalmawia.foursquare.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ashalmawia.foursquare.R
import com.ashalmawia.foursquare.actionBar
import com.ashalmawia.foursquare.navigator
import com.ashalmawia.foursquare.serviceLocator

private const val ARGUMENTS_RESTAURANT_ID = "RestaurantId"

class RestaurantDetailsFragment : Fragment() {

    companion object {
        fun arguments(restaurantId: String) = Bundle().apply {
            putString(ARGUMENTS_RESTAURANT_ID, restaurantId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)

        val serviceLocator = activity!!.serviceLocator
        val view = RestaurantDetailsViewImpl(root, actionBar, serviceLocator.imageLoader)
        val presenter = RestaurantDetailsPresenterImpl(view, serviceLocator.repository, navigator)

        val id = arguments?.restaurantId
        if (id != null) {
            presenter.showRestaurantWithId(id)
        } else {
            presenter.noRestaurantId()
        }
    }
}

private val Bundle.restaurantId
    get() = if (this.containsKey(ARGUMENTS_RESTAURANT_ID)) this.getString(ARGUMENTS_RESTAURANT_ID) else null