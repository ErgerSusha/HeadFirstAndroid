package com.tasks.erger.tasksonheadfirstandroidkotlin3

import android.os.Bundle
import android.content.Intent
import android.app.Activity
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.widget.*
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.CAR_NUMBER
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.DB_NAME
import kotlinx.android.synthetic.main.activity_top_level.*

class TopLevelActivity : Activity() {

    private var db: SQLiteDatabase? = null
    private var favoritesCursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_level)

        setListeners()
        setAdapter()
    }

    private fun getFavoritesCursor(): Cursor {
        return db!!.query(DB_NAME,
                arrayOf("_id", "NAME"),
                "FAVORITE = 1",
                null, null, null, null)
    }

    private fun setListeners() {
        val itemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                val intent = Intent(this@TopLevelActivity, CarsCategoryActivity::class.java)
                startActivity(intent)
            }
        }

        list_options.onItemClickListener = itemClickListener
    }

    private fun setAdapter() {
        try {
            db = CarsDatabaseHelper(this).readableDatabase

            list_favorites.adapter = SimpleCursorAdapter(this@TopLevelActivity,
                    android.R.layout.simple_list_item_1,
                    getFavoritesCursor(),
                    arrayOf("NAME"),
                    intArrayOf(android.R.id.text1),
                    0)

        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }

        list_favorites.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, id ->
            startActivity(Intent(this@TopLevelActivity,
                    CarsActivity::class.java).putExtra(CAR_NUMBER, id.toInt()))
        }
    }

    override fun onRestart() {
        super.onRestart()
        try {
            db = CarsDatabaseHelper(this).readableDatabase
            val newCursor = getFavoritesCursor()
            (list_favorites.adapter as CursorAdapter).changeCursor(newCursor)
            favoritesCursor = newCursor
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoritesCursor!!.close()
        db!!.close()
    }
}