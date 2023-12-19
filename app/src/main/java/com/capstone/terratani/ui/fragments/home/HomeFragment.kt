package com.capstone.terratani.ui.fragments.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.terratani.data.remote.response.Resource
import com.capstone.terratani.databinding.FragmentHomeBinding
import com.capstone.terratani.dummy.DataDummy
import com.capstone.terratani.ui.adapter.FeaturesRecyclerView
import com.capstone.terratani.ui.adapter.PlantRecyclerView
import com.capstone.terratani.ui.login.LoginActivity
import com.capstone.terratani.utils.DateFormat
import com.capstone.terratani.utils.checkPermission
import com.capstone.terratani.utils.createTimeStamp
import com.capstone.terratani.utils.determineWeatherIcon
import com.capstone.terratani.utils.getCurrentLocation
import com.capstone.terratani.utils.translateWeatherType
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var latitude: Double? = null
    private var longitude: Double? = null

    // First RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlantRecyclerView

    // Second RecylcerView
    private lateinit var secondRecyclerView: RecyclerView
    private lateinit var secondAdapter: FeaturesRecyclerView

    private lateinit var username: TextView
    private val firebaseAuth = FirebaseAuth.getInstance()

    // ViewModel
    private lateinit var viewModel: HomeViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        // First RecyclerView
        recyclerView = binding.rvPlant
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val allPlantsData = DataDummy.allPlants
        adapter = PlantRecyclerView(allPlantsData)
        recyclerView.adapter = adapter

        // Third RecyclerView
        secondRecyclerView = binding.rvFeatures
        val secondLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        secondRecyclerView.layoutManager = secondLayoutManager

        val featuresData = DataDummy.features
        secondAdapter = FeaturesRecyclerView(featuresData)
        secondRecyclerView.adapter = secondAdapter

        username = binding.tvSampleUsername
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            username.text = firebaseUser.displayName
        } else {
            navigateToLogin()
        }
        getUserLocation()
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun observeWeather(lat: Double, lon: Double) {
        viewModel.getWeatherByCoordinate(lat, lon).observe(this.viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val weather = it.data!!
                    val dayDate = createTimeStamp(DateFormat.DAY_DATE)
                    val time = createTimeStamp(DateFormat.TIME)
                    val weatherIcon = weather.weather?.get(0)?.let { it1 -> determineWeatherIcon(it1.main) }

                    binding.apply {
                        tvTemperature.text = weather.main?.temp?.toInt().toString() + "°C"
                        tvWeather.text = weather.weather?.get(0)?.let { it1 -> translateWeatherType(it1.main) }
                        tvCityWeather.text = weather.name
                        tvClock.text = time
                        tvDate.text = dayDate
                        ivWeatherIcon.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                weatherIcon!!,
                                null
                            )
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getCurrentLocation(requireContext()) {
                    latitude = it.latitude
                    longitude = it.longitude
                    observeWeather(latitude!!, longitude!!)
                }
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getCurrentLocation(requireContext()) {
                    latitude = it.latitude
                    longitude = it.longitude
                    observeWeather(latitude!!, longitude!!)
                }
            }
        }
    }

    private fun getUserLocation() {
        if (checkPermission(requireContext() , Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getCurrentLocation(requireContext()) {
                latitude = it.latitude
                longitude = it.longitude
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}