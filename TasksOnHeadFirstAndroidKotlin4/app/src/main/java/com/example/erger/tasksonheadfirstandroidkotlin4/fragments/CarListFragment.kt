package com.example.erger.tasksonheadfirstandroidkotlin4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.content.Context
import android.support.v4.app.ListFragment
import android.widget.ListView
import com.example.erger.tasksonheadfirstandroidkotlin4.data.Car

class CarListFragment : ListFragment() {

    internal interface CarListListener {
        fun itemClicked(id: Int)
    }
    private var listener: CarListListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val names = arrayOfNulls<String>(Car.cars.size)
        for (i in 0 until Car.cars.size) {
            names[i] = Car.cars[i].name
        }
        val adapter = ArrayAdapter<String>(
                inflater.context, android.R.layout.simple_list_item_1, names)
        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.listener = activity as CarListListener
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        if (listener != null) {
            listener!!.itemClicked(id.toInt())
        }
    }
}
