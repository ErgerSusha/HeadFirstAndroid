package com.example.erger.tasksonheadfirstandroidkotlin4

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Constants.Companion.CAR_DETAIL_ID
import com.example.erger.tasksonheadfirstandroidkotlin4.fragments.CarDetailFragment

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val carDetailFragment = supportFragmentManager.findFragmentById(R.id.detail_frag) as CarDetailFragment
        carDetailFragment.carId = intent.getIntExtra(CAR_DETAIL_ID, 0)
    }
}
