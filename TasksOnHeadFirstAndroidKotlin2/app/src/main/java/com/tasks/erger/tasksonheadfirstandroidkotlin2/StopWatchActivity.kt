package com.tasks.erger.tasksonheadfirstandroidkotlin2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tasks.erger.tasksonheadfirstandroidkotlin2.Constants.Companion.RUNNING
import com.tasks.erger.tasksonheadfirstandroidkotlin2.Constants.Companion.SECONDS
import com.tasks.erger.tasksonheadfirstandroidkotlin2.Constants.Companion.WAS_RUNNING
import kotlinx.android.synthetic.main.activity_stop_watch.*

class StopWatchActivity : AppCompatActivity() {
    private var seconds = 0
    private var running : Boolean = false
    private var wasRunning : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt(SECONDS)
            running = savedInstanceState.getBoolean(RUNNING)
            wasRunning = savedInstanceState.getBoolean(WAS_RUNNING)
        }

        start_button.setOnClickListener {
            running = true
        }

        stop_button.setOnClickListener {
            running = false
        }

        reset_button.setOnClickListener {
            running = false
            seconds = 0
        }

        runTimer()
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning)
            running = true
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.putInt(SECONDS, seconds)
        savedInstanceState.putBoolean(RUNNING, running)
        savedInstanceState.putBoolean(WAS_RUNNING, wasRunning)
    }

    private fun runTimer() {
        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                time_view.text = String.format("%02d:%02d:%02d",
                        seconds / 3600,
                        (seconds % 3600) / 60,
                        seconds %60)

                if (running)
                    seconds++
                handler.postDelayed(this, 1000)
            }
        })
    }
}