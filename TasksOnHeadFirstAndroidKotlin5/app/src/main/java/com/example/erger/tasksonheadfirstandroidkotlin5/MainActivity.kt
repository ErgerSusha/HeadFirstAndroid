package com.example.erger.tasksonheadfirstandroidkotlin5

import android.content.Intent
import android.os.Bundle
import android.content.res.Configuration
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.ShareActionProvider
import android.view.View
import android.widget.ArrayAdapter
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Constants.Companion.POSITION
import com.example.erger.tasksonheadfirstandroidkotlin5.data.Constants.Companion.VISIBLE_FRAGMENT
import com.example.erger.tasksonheadfirstandroidkotlin5.adapter_fragments.PastaFragment
import com.example.erger.tasksonheadfirstandroidkotlin5.fragments.PizzaMaterialFragment
import com.example.erger.tasksonheadfirstandroidkotlin5.adapter_fragments.StoresFragment
import com.example.erger.tasksonheadfirstandroidkotlin5.fragments.TopFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            selectItem(savedInstanceState.getInt(POSITION))
        } else {
            selectItem(0)
        }

        setDrawer()
        setBackStackListener()
    }

    private fun setDrawer() {
        drawer.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.titles))

        drawer.setOnItemClickListener { _, _, position, _ -> selectItem(position)}

        drawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, R.string.open_drawer, R.string.close_drawer) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
            }
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                invalidateOptionsMenu()
            }
        }
        drawer_layout.addDrawerListener(drawerToggle)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun setBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentByTag(VISIBLE_FRAGMENT)

            if (fragment is TopFragment) currentPosition = 0
            if (fragment is PizzaMaterialFragment) currentPosition = 1
            if (fragment is PastaFragment) currentPosition = 2
            if (fragment is StoresFragment) currentPosition = 3

            if (currentPosition == 0)
                supportActionBar!!.title = resources.getString(R.string.app_name)
            else
                supportActionBar!!.title = resources.getStringArray(R.array.titles)[currentPosition]

            drawer.setItemChecked(currentPosition, true)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.findItem(R.id.action_share).isVisible = !drawer_layout.isDrawerOpen(drawer)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun selectItem(position: Int) {
        val fragment: Fragment = when (position) {
            1 -> PizzaMaterialFragment()
            2 -> PastaFragment()
            3 -> StoresFragment()
            else -> TopFragment()
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, VISIBLE_FRAGMENT)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()

        if (position == 0)
            supportActionBar!!.title = resources.getString(R.string.app_name)
        else
            supportActionBar!!.title = resources.getStringArray(R.array.titles)[position]

        drawer_layout.closeDrawer(drawer)
        currentPosition = position
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(POSITION, currentPosition)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val item = menu!!.findItem(R.id.action_share) as MenuItem
        val shareAction = MenuItemCompat.getActionProvider(item) as ShareActionProvider
        val shareIntent = Intent(Intent.ACTION_SEND).setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, "Hello!").setType("text/plain")
        shareAction.setShareIntent(shareIntent)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        when(item!!.itemId) {
            R.id.action_create_order -> startActivity(Intent(this,
                    OrderActivity::class.java))
            R.id.action_settings -> return true
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}
