package com.example.erger.tasksonheadfirstandroidkotlin5

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.support.v7.widget.ShareActionProvider
import android.content.Intent
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Constants.Companion.CAR_NUMBER
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Pizza
import kotlinx.android.synthetic.main.activity_pizza_detail.*

class PizzaDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val pizzaNo = intent.extras!!.get(CAR_NUMBER) as Int
        val pizzaName = Pizza.cars[pizzaNo].name
        val imageView = findViewById<View>(R.id.pizza_image) as ImageView

        pizza_text.text = pizzaName
        imageView.setImageDrawable(resources.getDrawable(Pizza.cars[pizzaNo].imageResourceId))
        imageView.contentDescription = pizzaName
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, pizza_text.text)
        (MenuItemCompat.getActionProvider(menu!!.findItem(R.id.action_share) as MenuItem)
                as ShareActionProvider).setShareIntent(intent)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_create_order -> {
                startActivity(Intent(this, OrderActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}