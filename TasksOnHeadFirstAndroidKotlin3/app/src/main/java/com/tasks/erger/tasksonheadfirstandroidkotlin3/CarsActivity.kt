package com.tasks.erger.tasksonheadfirstandroidkotlin3

import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.os.AsyncTask
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.FAVORITE
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.CAR_NUMBER
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.DB_NAME
import kotlinx.android.synthetic.main.activity_cars.*
import java.lang.ref.WeakReference

class CarsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars)

        val carNo = intent.extras!!.get(CAR_NUMBER) as Int

        try {
            val carsDatabaseHelper = CarsDatabaseHelper(this)
            val db = carsDatabaseHelper.writableDatabase
            val cursor = db.query(DB_NAME,
                    arrayOf("NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"),
                    "_id = ?",
                    arrayOf(carNo.toString()),
                    null, null, null)
            if (cursor.moveToFirst()) {
                val nameText = cursor.getString(0)
                photo.contentDescription = nameText
                photo.setImageResource(cursor.getInt(2))
                name.text = nameText
                description.text = cursor.getString(1)
                favorite.isChecked = (cursor.getInt(3) == 1)
            }
            cursor.close()
            db.close()
        } catch(e: SQLiteException) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }

        favorite.setOnClickListener {
            UpdateCarTask(this).execute(intent.extras!!.get(CAR_NUMBER) as Int)
        }
    }

    private class UpdateCarTask internal constructor(context: CarsActivity): AsyncTask<Int, Void, Boolean>() {
        lateinit var carsValues: ContentValues
        private val activityReference: WeakReference<CarsActivity> = WeakReference(context)

        override fun onPreExecute() {
            val activity = activityReference.get()
            carsValues = ContentValues()
            carsValues.put(FAVORITE, activity!!.findViewById<CheckBox>(R.id.favorite).isChecked)
        }

        override fun doInBackground(vararg cars: Int?): Boolean? {
            return try {
                val db =
                        CarsDatabaseHelper(activityReference.get()!!.applicationContext).writableDatabase
                db.update(DB_NAME,
                        carsValues,
                        "_id = ?",
                        arrayOf(cars[0].toString()))
                db.close()
                true
            } catch (e: SQLiteException) {
                false
            }
        }

        override fun onPostExecute(result: Boolean?) {
            if (!result!!)
                Toast.makeText(activityReference.get()!!.applicationContext,
                        "Database unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}
