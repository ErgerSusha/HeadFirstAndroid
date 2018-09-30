package com.example.erger.tasksonheadfirstandroidkotlin5.data

import com.example.erger.tasksonheadfirstandroidkotlin5.R

class Pizza constructor(val name: String, val imageResourceId: Int) {
    companion object {
        val cars = arrayOf(Pizza("Audi RS6", R.drawable.rs6),
                Pizza("Audi RS7", R.drawable.rs7))
    }
}