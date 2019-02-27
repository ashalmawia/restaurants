package com.ashalmawia.foursquare.features.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ashalmawia.foursquare.navigator

private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
private const val REQUEST_CODE = 1512

class LocationPermissionFragment : Fragment(), PermissionChecker {

    private var presenter: LocationPermissionPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        val view = LocationPermissionViewImpl(activity!!, navigator)
        val presenter = LocationPermissionPresenterImpl(LOCATION_PERMISSION, view, this)
        view.presenter = presenter

        this.presenter = presenter

        presenter.start()
    }

    override fun checkPermissionSilently(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity!!, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun canRequestPermission(permission: String): Boolean {
        return shouldShowRequestPermissionRationale(permission)
    }

    override fun requestPermission(permission: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            presenter?.onPermissionGranted()
        } else {
            requestPermissions(arrayOf(permission), REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != REQUEST_CODE) {
            return
        }

        val granted = !grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (granted) {
            presenter?.onPermissionGranted()
        } else {
            presenter?.onPermissionDenied()
        }
    }
}