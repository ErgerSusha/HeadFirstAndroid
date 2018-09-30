package com.example.erger.tasksonheadfirstandroidkotlin4.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Constants.Companion.RUNNING
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Constants.Companion.SECONDS
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Constants.Companion.WAS_RUNNING
import com.example.erger.tasksonheadfirstandroidkotlin4.R
import kotlinx.android.synthetic.main.fragment_timer.*

class TimerFragment : Fragment(), View.OnClickListener {
    private var seconds: Int = 0
    private var running: Boolean = false
    private var wasRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt(SECONDS)
            wasRunning = savedInstanceState.getBoolean(WAS_RUNNING)
            if (wasRunning) {
                running = true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout: View = inflater.inflate(R.layout.fragment_timer, container, false)
        runTimer(layout)
        val startButton = layout.findViewById(R.id.start_button) as Button
        startButton.setOnClickListener(this)
        val stopButton = layout.findViewById(R.id.stop_button) as Button
        stopButton.setOnClickListener(this)
        val resetButton = layout.findViewById(R.id.reset_button) as Button
        resetButton.setOnClickListener(this)
        return layout
    }

    private fun onClickStart(view: View) {
        val data = time_edit_text.text.toString()
        if (data != "" && seconds == 0 && !running) {
            seconds = (data.toDouble() * 31536000).toInt()
        }
        if (seconds > 0) {
            running = true
        }
    }

    private fun onClickStop(view: View) {
        running = false
    }

    private fun onClickReset(view: View) {
        running = false
        seconds = 0
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.start_button -> onClickStart(v)
            R.id.stop_button -> onClickStop(v)
            R.id.reset_button -> onClickReset(v)
        }
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SECONDS, seconds)
        outState.putBoolean(RUNNING, running)
        outState.putBoolean(WAS_RUNNING, wasRunning)
    }

    private fun runTimer(view: View) {
        val timeView = view.findViewById(R.id.time_view) as TextView
        val handler = Handler()
        handler.post( object : Runnable {
            override fun run() {
                timeView.text = String.format("%d seconds remained", seconds)
                if (running && seconds > 0) {
                    --seconds
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}
