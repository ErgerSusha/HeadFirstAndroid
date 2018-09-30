package com.tasks.erger.tasksonheadfirstandroidkotlin3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.DB_NAME
import com.tasks.erger.tasksonheadfirstandroidkotlin3.Constants.Companion.DB_VERSION

class CarsDatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        updateMyDatabase(db, oldVersion, newVersion)
    }

    override fun onCreate(db: SQLiteDatabase) {
        updateMyDatabase(db, 0, DB_VERSION)
    }

    private fun updateMyDatabase(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            fillDatabase(db)
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE CARS ADD COLUMN FAVORITE NUMERIC")
        }
    }

    private fun insertCar(db: SQLiteDatabase, name: String, description: String, resourceId: Int) {
        val carsValues = ContentValues()
        carsValues.put("NAME", name)
        carsValues.put("DESCRIPTION", description)
        carsValues.put("IMAGE_RESOURCE_ID", resourceId)
        db.insert(DB_NAME, null, carsValues)
    }

    private fun fillDatabase(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE CARS (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT, DESCRIPTION TEXT, IMAGE_RESOURCE_ID INTEGER);")

        insertCar(db, "Audi A4", "The Audi A4 is a line of" +
                " compact executive cars produced since 1994 by the German" +
                " car manufacturer Audi, a subsidiary of the Volkswagen Group.",
                R.drawable.a4)
        insertCar(db, "Audi A6", "The Audi A6 is an executive car" +
                " made by the German automaker Audi, now in its fifth generation.",
                R.drawable.a6)
        insertCar(db, "Audi A8", "The Audi A8 is a four-door, full-size, " +
                "luxury sedan manufactured and marketed by the German automaker Audi since 1994. ",
                R.drawable.a8)
    }
}