package com.milaparsia.rsia

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.milaparsia.rsia.models.AmbulanceResponse
import com.milaparsia.rsia.network.NetworkConfig
import com.milaparsia.rsia.sharepreflogin.SharedPrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_maps_ambulance.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsAmbulanceActivity : AppCompatActivity(), OnMapReadyCallback {
    var currentLocation : Location? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lat: String? = null
    private var long: String? = null
    val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_ambulance)

        btn_back_arrow.setOnClickListener {
            finish()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()

        btn_ambulance_kirim_lokasi.setOnClickListener {
            val no_rek_medis = SharedPrefManager.getInstance(this).login.no_rkm_medis
            val longitude = long.toString()
            val latitude = lat.toString()
            val keluhan = et_ambulance_keluhan.text.toString().trim()

            if(keluhan.isEmpty()){
                et_ambulance_keluhan.error = "Silahkan Isi Keluhan"
            }else{
                pb_ambulance_send.visibility = View.VISIBLE
                NetworkConfig.getInstance().sendLocation(no_rek_medis, longitude, latitude, keluhan)
                    .enqueue(object: Callback<AmbulanceResponse>{
                        override fun onResponse(
                            call: Call<AmbulanceResponse>,
                            response: Response<AmbulanceResponse>
                        ) {
                            pb_ambulance_send.visibility = View.GONE
                            et_ambulance_keluhan.text.clear()

                            val builder = AlertDialog.Builder(this@MapsAmbulanceActivity)
                            builder.setTitle(response.body()?.metaData?.message.toString())
                            builder.setMessage(response.body()?.response?.ambulance.toString())
                                .setCancelable(false)
                                .setPositiveButton("Ya"){  dialog, id ->
                                    finish()
                                }

                            val alert = builder.create()
                            alert.show()

                        }

                        override fun onFailure(call: Call<AmbulanceResponse>, t: Throwable) {
                            Log.e("Network Error", t.message.toString())
                        }

                    })
            }
        }
    }

    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            this?.let { it1 -> ActivityCompat.requestPermissions(it1, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101) }

        }

        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)
                supportMapFragment!!.getMapAsync(this)
                lat = currentLocation!!.latitude.toString()
                long = currentLocation!!.longitude.toString()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        val name = SharedPrefManager.getInstance(this).login.nm_pasien
        val markerOptions = MarkerOptions().position(latLng).title(name)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
        googleMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}