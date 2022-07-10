package com.example.filmfinder.maps

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.filmfinder.R
import com.example.filmfinder.databinding.FragmentMapsBinding
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding: FragmentMapsBinding
        get() {
            return _binding!!
        }

    private lateinit var map: GoogleMap
    private lateinit var geofenceClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper

    private val cinemas = listOf<LatLng>(
        LatLng(56.836239, 60.595842), LatLng(56.829286, 60.598727),
        LatLng(56.837134, 60.621932), LatLng(56.863279, 60.629861),
        LatLng(56.832598, 60.582357), LatLng(56.829317, 60.673536)
    )

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        googleMap.setOnMapLongClickListener {
            getAddress(it)
            map.addMarker(MarkerOptions().position(it))
        }
        googleMap.isMyLocationEnabled = true
        addCinemasGeofences()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        geofenceClient = LocationServices.getGeofencingClient(requireContext())
        geofenceHelper = GeofenceHelper(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.fragmentMapsButtonSearch.setOnClickListener { getAddressFromSearch(binding.fragmentMapsSearchAddress.text.toString()) }
    }

    private fun addCinemasGeofences() {
        for (cinema in cinemas) {
            addGeofence(cinema, 200f)
            map.addMarker(MarkerOptions().position(cinema))
            addCircle(cinema, 200f)
        }
    }

    private fun addCircle(latLng: LatLng, radius: Float) {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0))
        circleOptions.fillColor(Color.argb(64, 255, 0, 0))
        circleOptions.strokeWidth(4f)
        map.addCircle(circleOptions)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
                    PackageManager.PERMISSION_GRANTED -> {
                getLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showDialog()
            }
            else -> {
                requestPermission()
            }
        }
    }

    private val REQUEST_CODE = 222

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            when {
                permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED -> getLocation()
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> showDialog()
                else -> {
                    Toast.makeText(requireContext(), "Need permissions!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к местоположению")
            .setMessage("Для взаимодействия с картой нужно выдать соответствующие разрешения!")
            .setPositiveButton("Предоставить доступ") { _, _ -> requestPermission() }
            .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private val REFRESH_PERIOD = 1000L
    private val MIN_DISTANCE = 100F
    private val locationListener = LocationListener { location ->
        val newLocation = LatLng(location.latitude, location.longitude)
        getAddress(newLocation)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 12f))
    }

    private fun getAddress(location: LatLng) {
        Thread {
            val geocoder = Geocoder(requireContext())
            val listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            requireActivity().runOnUiThread {
                binding.fragmentMapsTextAddress.text = listAddress.last().getAddressLine(0)
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location.latitude,
                            location.longitude
                        ), 12f
                    )
                )
            }
        }.start()
    }

    private fun getAddressFromSearch(address: String) {
        Thread {
            val geocoder = Geocoder(requireContext())
            val listAddress = geocoder.getFromLocationName(address, 1)
            val address = listAddress.last()
            requireActivity().runOnUiThread {
                binding.fragmentMapsTextAddress.text = address.getAddressLine(0)
                map.moveCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            address.latitude,
                            address.longitude
                        )
                    )
                )
            }
        }.start()
    }

    private fun addGeofence(latLng: LatLng, radius: Float) {
        val geofence =
            geofenceHelper.getGeofence(latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER)
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
        val pendingIntent = geofenceHelper.getPendingIntent()
        geofenceClient.addGeofences(geofencingRequest, pendingIntent!!)
    }

    private fun getLocation() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    gpsProvider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MIN_DISTANCE,
                            locationListener
                        )
                    }
                } else {
                    val lastLocation =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lastLocation?.let { getAddress(LatLng(it.latitude, it.longitude)) }
                }
            }
        }
    }
}