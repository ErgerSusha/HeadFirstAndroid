package com.example.erger.tasksonheadfirstandroidkotlin7

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import android.os.Bundle
import android.widget.Toast

class OdometerService : Service() {
    private val binder = OdometerBinder()
    private var distanceInMeters: Double = 0.toDouble()
    private var lastLocation: Location? = null
    internal val miles
        get() = this.distanceInMeters / 1609.344

    inner class OdometerBinder : Binder() {
        internal val odometer: OdometerService
            get() = this@OdometerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (lastLocation == null)
                    lastLocation = location
                distanceInMeters += location.distanceTo(lastLocation)
                lastLocation = location
            }

            override fun onProviderDisabled(arg0: String) {}
            override fun onProviderEnabled(arg0: String) {}
            override fun onStatusChanged(arg0: String, arg1: Int, bundle: Bundle) {}
        }

        try {
            val locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, 1.toFloat(), listener)
        } catch (e: SecurityException) {
            Toast.makeText(applicationContext, "GPS is unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}
