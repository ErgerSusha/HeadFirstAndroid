package com.example.erger.tasksonheadfirstandroidkotlin7

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var odometer: OdometerService? = null
    private var bound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        watchMileage()
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            odometer = (service as OdometerService.OdometerBinder).odometer
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(Intent(this, OdometerService::class.java),
                connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }

    private fun watchMileage() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                var distance = 0.0
                if (odometer != null)
                    distance = odometer!!.miles
                val distanceStr = String.format("%1$,.2f miles", distance)
                distance_view.text = distanceStr
                handler.postDelayed(this, 1000)
            }
        })
    }
}