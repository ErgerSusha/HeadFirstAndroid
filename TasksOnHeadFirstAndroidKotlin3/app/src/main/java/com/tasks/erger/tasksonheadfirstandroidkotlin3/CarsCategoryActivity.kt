package com.tasks.erger.tasksonheadfirstandroidkotlin3

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.CAR_NUMBER
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.DB_NAME

class CarsCategoryActivity : ListActivity() {
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val carsList = listView
        setCarsList(carsList)
    }

    private fun setCarsList(listView: ListView) {
        try {
            val carsDatabaseHelper = CarsDatabaseHelper(this)
            db = carsDatabaseHelper.readableDatabase

            cursor = db!!.query(DB_NAME,
                    arrayOf("_id", "NAME"),
                    null, null, null, null, null)

            listView.adapter = SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    arrayOf("NAME"),
                    intArrayOf(android.R.id.text1),
                    0)
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor!!.close()
        db!!.close()
    }

    override fun onListItemClick(listView: ListView?, itemView: View?, position: Int, id: Long) {
        val intent = Intent(this@CarsCategoryActivity, CarsActivity::class.java)
        intent.putExtra(CAR_NUMBER, id.toInt())
        startActivity(intent)
    }
}
