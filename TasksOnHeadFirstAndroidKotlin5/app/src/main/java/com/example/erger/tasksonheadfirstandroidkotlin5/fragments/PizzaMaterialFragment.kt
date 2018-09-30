package com.example.erger.tasksonheadfirstandroidkotlin5.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.support.v4.app.Fragment
import com.example.erger.tasksonheadfirstandroidkotlin5.CaptionedImagesAdapter
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Pizza
import com.example.erger.tasksonheadfirstandroidkotlin5.PizzaDetailActivity
import com.example.erger.tasksonheadfirstandroidkotlin5.R
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Constants.Companion.CAR_NUMBER

class PizzaMaterialFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val size = Pizza.cars.size
        val pizzaNames = arrayOfNulls<String>(size)
        val pizzaImages = arrayOfNulls<Int>(size)
        for (i in 0 until pizzaNames.size) {
            pizzaNames[i] = Pizza.cars[i].name
            pizzaImages[i] = Pizza.cars[i].imageResourceId
        }

        val adapter = CaptionedImagesAdapter(pizzaNames, pizzaImages)
        val pizzaRecycler = inflater.inflate(R.layout.fragment_pizza_material,
                container, false) as RecyclerView
        pizzaRecycler.adapter = adapter
        pizzaRecycler.layoutManager = LinearLayoutManager(activity)
        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                startDetailActivity(position)
            }
        })

        return pizzaRecycler
    }

    private fun startDetailActivity(position: Int) {
        val intent = Intent(activity, PizzaDetailActivity::class.java)
        intent.putExtra(CAR_NUMBER, position)
        activity!!.startActivity(intent)
    }
}