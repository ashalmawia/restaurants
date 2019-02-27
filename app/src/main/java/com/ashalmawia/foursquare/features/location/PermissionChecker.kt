package com.ashalmawia.foursquare.features.location

interface PermissionChecker {

    fun checkPermissionSilently(permission: String): Boolean

    fun canRequestPermission(permission: String): Boolean

    fun requestPermission(permission: String)
}