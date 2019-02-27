package com.ashalmawia.foursquare.features.location

interface LocationPermissionPresenter {

    fun start()

    fun refresh()

    fun requestPermission()

    fun onPermissionGranted()

    fun onPermissionDenied()
}

class LocationPermissionPresenterImpl(
    private val locationPermissionName: String,
    private val view: LocationPermissionView,
    private val permissionChecker: PermissionChecker
) : LocationPermissionPresenter {

    override fun start() {
        checkLocationPermission()
    }

    override fun refresh() {
        val granted = permissionChecker.checkPermissionSilently(locationPermissionName)
        if (granted) {
            onPermissionGranted()
        }
    }

    private fun checkLocationPermission() {
        permissionChecker.requestPermission(locationPermissionName)
    }

    private fun tryGetPermission() {
        val canRequest = permissionChecker.canRequestPermission(locationPermissionName)
        if (canRequest) {
            view.showRationale()
        } else {
            // the user has denied the permission and marked 'Never ask again',
            // let's mention we are sorry about that
            view.showGoToSettingsCTA()
        }
    }

    override fun requestPermission() {
        permissionChecker.requestPermission(locationPermissionName)
    }

    override fun onPermissionGranted() {
        view.closeAllDialogs()
        view.goToNextScreen()
    }

    override fun onPermissionDenied() {
        tryGetPermission()
    }
}