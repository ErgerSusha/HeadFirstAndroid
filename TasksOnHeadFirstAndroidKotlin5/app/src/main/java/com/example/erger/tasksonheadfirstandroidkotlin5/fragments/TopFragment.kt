package com.example.erger.tasksonheadfirstandroidkotlin5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import com.example.erger.tasksonheadfirstandroidkotlin5.CaptionedImagesAdapter
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Pizza
import com.example.erger.tasksonheadfirstandroidkotlin5.PizzaDetailActivity
import com.example.erger.tasksonheadfirstandroidkotlin5.R
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Constants.Companion.CAR_NUMBER

class TopFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_top, container, false) as RelativeLayout
        val pizzaRecycler = layout.findViewById<View>(R.id.pizza_recycler) as RecyclerView
        val pizzaNames = arrayOfNulls<String>(2)
        val pizzaImages = arrayOfNulls<Int>(2)

        for (i in 0..1) {
            pizzaNames[i] = Pizza.cars[i].name
            pizzaImages[i] = Pizza.cars[i].imageResourceId
        }

        pizzaRecycler.layoutManager = GridLayoutManager(activity, 2)
        val adapter = CaptionedImagesAdapter(pizzaNames, pizzaImages)
        pizzaRecycler.adapter = adapter
        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
               startDetailActivity(position)
            }
        })
        
        return layout
    }

    private fun startDetailActivity(position: Int) {
        val intent = Intent(activity, PizzaDetailActivity::class.java)
        intent.putExtra(CAR_NUMBER, position)
        activity!!.startActivity(intent)
    }
}