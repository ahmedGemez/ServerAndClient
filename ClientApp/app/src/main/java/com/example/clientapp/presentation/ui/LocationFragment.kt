package com.example.clientapp.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.clientapp.databinding.FragmentLocationBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class LocationFragment : Fragment() {


    private var mLocationManagerGPS: LocationManager? = null
    private var mLocationListenerGPS: LocationListener? = null
    private lateinit var fragmentHomeBinding: FragmentLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentLocationBinding.inflate(inflater, container, false)
        getPositionGPS()

        fragmentHomeBinding.button3.setOnClickListener {

            if(isGPSEnabled(requireContext()))
            getPositionGPS()
            else
              Toast.makeText(requireContext(),"Please Open your GPs",Toast.LENGTH_LONG).show()

        }


        return fragmentHomeBinding.root
    }


    private fun getPositionGPS() {
        mLocationManagerGPS =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationListenerGPS = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                fragmentHomeBinding.latValue.text = location.latitude.toString()
                fragmentHomeBinding.longValue.text = location.longitude.toString()

            }
            override fun onStatusChanged(
                provider: String,
                status: Int,
                extras: Bundle
            ) {
            }
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                requestLocationPermission()
            } else {
                mLocationManagerGPS?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5,
                    0f,
                    mLocationListenerGPS as LocationListener
                )
            }
        }
    }


    private fun requestLocationPermission() {

        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    getPositionGPS()
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()


    }

    fun isGPSEnabled(mContext: Context): Boolean {
        val locationManager =
            mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationManagerGPS?.removeUpdates(mLocationListenerGPS!!);
    }
}