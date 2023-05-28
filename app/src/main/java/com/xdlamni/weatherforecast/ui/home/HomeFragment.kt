package com.xdlamni.weatherforecast.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.xdlamni.weatherforecast.databinding.FragmentHomeBinding
import com.xdlamni.weatherforecast.helpers.PermissionHelperInterface
import com.xdlamni.weatherforecast.helpers.PermissionHelpers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var fragmentActivity: FragmentActivity
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentActivity = context as FragmentActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableUserLocationSettings()
        observeWeatherForecast()
    }

    private fun observeWeatherForecast() {
        homeViewModel.weatherForecast.observe(viewLifecycleOwner) { dailyForecastList ->
            binding.txtHeader.text = dailyForecastList[0].city
        }
    }
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireContext())
        val locationRequest = LocationRequest.Builder(300000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0.lastLocation?.let { location ->
                    processLocation(location)
                }
            }
        }
        fusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun processLocation(location: Location) {
        val lat: Double = location.latitude
        val lon: Double = location.longitude

        homeViewModel.getDailyForecastAtLocation(lat, lon)

    }
    private fun enableUserLocationSettings() {
        PermissionHelpers().requestLocationPermission(fragmentActivity,
            object: PermissionHelperInterface{
                override fun isLocationPermGranted(isGranted: Boolean) {
                    if (isGranted && isLocationSettingEnabled()){
                        getCurrentLocation()
                    } else {
                        // Actually display the error widget
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        requireContext().startActivity(intent)
                    }
                }
            })
    }

    private fun isLocationSettingEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}