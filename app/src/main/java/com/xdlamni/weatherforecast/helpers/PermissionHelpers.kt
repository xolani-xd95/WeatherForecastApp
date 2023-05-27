package com.xdlamni.weatherforecast.helpers

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity

class PermissionHelpers {
    fun requestLocationPermission(fragment: FragmentActivity, permHelperInterface: PermissionHelperInterface) {
        val requestPermissionLauncher = fragment.registerForActivityResult( ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            permHelperInterface.isLocationPermGranted(isGranted)
        }
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

