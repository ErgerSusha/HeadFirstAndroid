package com.example.erger.tasksonheadfirstandroidkotlin4.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Car
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Constants.Companion.CAR_ID
import com.example.erger.tasksonheadfirstandroidkotlin4.R
import kotlinx.android.synthetic.main.fragment_car_detail.*

class CarDetailFragment : Fragment() {
    var carId : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            carId = savedInstanceState.getInt(CAR_ID)
        } else {
            val ft = childFragmentManager.beginTransaction()
            val timerFragment = TimerFragment()
            ft.replace(R.id.timer_container, timerFragment)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }

        return inflater.inflate(R.layout.fragment_car_detail, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CAR_ID, carId)
    }

    override fun onStart() {
        super.onStart()
        if (view != null) {
            textTitle.text = Car.cars[carId].name
            textDescription.text = Car.cars[carId].description
        }
    }
}
