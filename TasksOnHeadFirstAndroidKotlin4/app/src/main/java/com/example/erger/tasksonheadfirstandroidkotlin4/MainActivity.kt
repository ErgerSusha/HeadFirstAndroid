package com.example.erger.tasksonheadfirstandroidkotlin4

import android.os.Bundle
import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Constants.Companion.CAR_DETAIL_ID
import com.example.erger.tasksonheadfirstandroidkotlin4.fragments.CarDetailFragment
import com.example.erger.tasksonheadfirstandroidkotlin4.fragments.CarListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CarListFragment.CarListListener {

    override fun itemClicked(id: Int) {
        if (frag_container != null) {
            val details = CarDetailFragment()
            details.carId = id
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, details)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(CAR_DETAIL_ID, id)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}