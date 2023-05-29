package com.xdlamni.weatherforecast.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.xdlamni.weatherforecast.R
import com.xdlamni.weatherforecast.api.ApiResponse
import com.xdlamni.weatherforecast.api.dto.DailyForecastDTO
import com.xdlamni.weatherforecast.databinding.FragmentHomeBinding
import com.xdlamni.weatherforecast.helpers.MapperHelpers
import com.xdlamni.weatherforecast.helpers.loadWithGlide
import com.xdlamni.weatherforecast.helpers.mapDailyDtoToUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var fragmentActivity: FragmentActivity
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: DailyForecastItemAdapter
    private lateinit var currentUserLocation: Location
    private var isResumedFromMinimised = false

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
        setUpRecyclerView()
        getCurrentLocation()
        observeWeatherForecast()
    }

    private fun isLocationSettingsEnabled(): Boolean {
        val isLocationPermGranted = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocationSettingEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isLocationSettingEnabled && isLocationPermGranted
    }
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if(isLocationSettingsEnabled()) {
            binding.errorView.root.isVisible = false
            fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireContext())
            val locationRequest = LocationRequest.Builder(300000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    p0.lastLocation?.let { location ->
                        currentUserLocation = location
                        processLocation(location)
                    }
                }
            }
            fusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            binding.errorView.root.isVisible = true
            binding.errorView.btnSettings.setOnClickListener {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                requireContext().startActivity(intent)
            }
        }
    }
    fun processLocation(location: Location) {
        val lat: Double = location.latitude
        val lon: Double = location.longitude

        homeViewModel.getDailyForecastAtLocation(lat, lon)
    }

    private fun setUpRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.crdDailyForecast.rvDailyForecast.layoutManager = layoutManager
    }
    private fun observeWeatherForecast() {
        homeViewModel.loadingState.observe(viewLifecycleOwner) {isLoading ->
            binding.weatherLayout.isVisible = !isLoading
            binding.loader.isVisible = isLoading
        }

        homeViewModel.weatherForecast.observe(viewLifecycleOwner) { dailyForecastList ->
            when(dailyForecastList) {
                is ApiResponse.Success -> {
                    updateForecastView(dailyForecastList.data)
                }
                is ApiResponse.Error -> {
                    binding.weatherLayout.isVisible = false
                    binding.errorView.root.isVisible = true
                    updateForecastErrorView(dailyForecastList.code)
                }
            }
        }
    }

    private fun updateForecastErrorView(errorCode: Int) {
        when(errorCode) {
            in 400..499 -> {
                binding.errorView.txtAppErrorBody.text = resources.getString(R.string.try_again_message)
                binding.errorView.btnSettings.text = resources.getString(R.string.try_again)
                binding.errorView.btnSettings.setOnClickListener {
                    binding.errorView.root.isVisible = false
                    binding.loader.isVisible = true
                    getCurrentLocation() // In case something went wrong with the request, restart the whole process of getting user location and fetching forecast
                }
            }
            in 500..599 -> {
                binding.errorView.txtAppErrorBody.text = resources.getString(R.string.try_again_network)
                binding.errorView.btnSettings.text = resources.getString(R.string.try_again)
                binding.errorView.btnSettings.setOnClickListener {
                    binding.errorView.root.isVisible = false
                    binding.loader.isVisible = true
                    processLocation(currentUserLocation) // Chances are, we were able to get the location, but encountered server issues. So in this case retry the API call only
                }
            }
        }
    }
    private fun updateForecastView(dailyForecast: DailyForecastDTO) {
        val mappedDailyForecast = dailyForecast.mapDailyDtoToUIModel()

        binding.todayForecastHeader.txtHeader.text = mappedDailyForecast[0].city
        binding.todayForecastHeader.imgTodayTemp.loadWithGlide(mappedDailyForecast[0].tempIcon)
        binding.todayForecastHeader.txtTodayTemp.text = mappedDailyForecast[0].maxTemp
        binding.todayForecastHeader.txtTodayTempDesc.text = mappedDailyForecast[0].description
        adapter = DailyForecastItemAdapter(mappedDailyForecast)
        binding.crdDailyForecast.rvDailyForecast.adapter = adapter
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        isResumedFromMinimised = true
    }
    override fun onResume() {
        super.onResume()
        if(isResumedFromMinimised) {
            getCurrentLocation()
        }
    }
}