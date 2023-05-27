package com.xdlamni.weatherforecast.ui

import android.Manifest
import android.content.Context
import android.location.LocationManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {
    fun isLocationEnabled(): Boolean {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun isLocationPermGranted() {
        val requestPermissionLauncher = this.registerForActivityResult( ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            isLocationGranted(isGranted)
        }
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}