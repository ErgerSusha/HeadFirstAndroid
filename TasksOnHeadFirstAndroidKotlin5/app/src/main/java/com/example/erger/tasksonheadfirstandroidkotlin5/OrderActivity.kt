package com.example.erger.tasksonheadfirstandroidkotlin5

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
