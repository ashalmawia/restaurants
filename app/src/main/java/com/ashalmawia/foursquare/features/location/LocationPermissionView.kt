package com.ashalmawia.foursquare.features.location

import android.app.AlertDialog
import android.content.Context
import com.ashalmawia.foursquare.Navigator
import com.ashalmawia.foursquare.R

interface LocationPermissionView {

    fun goToNextScreen()

    fun showRationale()

    fun showGoToSettingsCTA()

    fun closeAllDialogs()

    fun leave()
}

class LocationPermissionViewImpl(
    private val context: Context,
    private val navigator: Navigator
) : LocationPermissionView {

    private var rationale: AlertDialog? = null
    private var ctaDialog: AlertDialog? = null

    lateinit var presenter: LocationPermissionPresenter

    override fun goToNextScreen() {
        navigator.openRestaurantsMap()
    }

    override fun showRationale() {
        val rationale = AlertDialog.Builder(context)
            .setTitle(R.string.permission__location__rationale_title)
            .setMessage(R.string.permission__location__rationale_message)
            .setPositiveButton(R.string.permission__location__grant) { _, _ -> requestPermission() }
            .setNegativeButton(R.string.permission__location__leave) { _, _ -> leave() }
            .create()
        rationale.show()

        this.rationale = rationale
    }

    private fun requestPermission() {
        presenter.requestPermission()
    }

    override fun showGoToSettingsCTA() {
        val ctaDialog = AlertDialog.Builder(context)
            .setTitle(R.string.permission__location__rationale_title)
            .setMessage(R.string.permission__location__denied_forever_message)
            .setPositiveButton(R.string.permission__location__settings) { _, _ -> openAppSettings() }
            .setNegativeButton(R.string.permission__location__leave) { _, _ -> leave() }
            .create()
        ctaDialog.show()

        this.ctaDialog = ctaDialog
    }

    override fun closeAllDialogs() {
        rationale?.dismiss()
        ctaDialog?.dismiss()
    }

    override fun leave() {
        navigator.leave()
    }

    private fun openAppSettings() {
        navigator.openAppSettings()
    }
}