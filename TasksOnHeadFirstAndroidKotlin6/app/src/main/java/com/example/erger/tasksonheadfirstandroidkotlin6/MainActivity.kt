package com.example.erger.tasksonheadfirstandroidkotlin6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            startService(Intent(this, DelayedMessageService::class.java)
                    .putExtra(DelayedMessageService.EXTRA_MESSAGE,
                            resources.getString(R.string.button_response)))
        }
    }
}