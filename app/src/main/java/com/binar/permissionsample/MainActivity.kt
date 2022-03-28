package com.binar.permissionsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    //inisiasi button (Mendefinisikan)
    private lateinit var loadImageButton: Button
    private lateinit var requestLocation: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Tahapan meng-casting
        loadImageButton = findViewById(R.id.load_image_button)
        imageView = findViewById(R.id.image_view)
        requestLocation = findViewById(R.id.request_permission_button)
        //Tahapan agar button dapat di klik
       loadImageButton.setOnClickListener {
           loadImage()
       }
        requestLocation.setOnClickListener {
            requestPermission()
        }
    }
    fun loadImage(){
            Glide.with(this)
                .load("https://img.icons8.com/plasticine/2x/flower.png")
                .circleCrop()
                .into(imageView)
    }
    fun requestLocationPermission(){
       val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
           Toast.makeText(this,"Permission Location DIIZINKAN",Toast.LENGTH_LONG).show()
            getLongLat()
        } else {
            Toast.makeText(this,"Permission Location DITOLAK",Toast.LENGTH_LONG).show()
            requestLocationPermission()
        }
    }
    @SuppressLint("MissingPermission")
    fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        Toast.makeText(this,"Lat: ${location?.longitude}", Toast.LENGTH_LONG).show()
    }
    fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            201->{
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"ALLOW telah dipilih", Toast.LENGTH_LONG).show()
                    getLongLat()
                } else{
                    Toast.makeText(this,"DENY telah dipilih", Toast.LENGTH_LONG).show()
                }
            }
             else->{
                 Toast.makeText(this,"Bukan request yang dikirim", Toast.LENGTH_LONG).show()
             }
        }
    }

}