package com.example.gymlogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_timer.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(R.layout.activity_timer.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
    }
}