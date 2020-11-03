package com.log.worldholidays.view

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.log.worldholidays.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        val w: Window = window // in Activity's onCreate() for instance

        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/

        bottom_navigationView_stuff()

    }

    private fun bottom_navigationView_stuff() {

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar_bottom)
        val navController = findNavController(R.id.nav_host_fragment_container)

        bottomNavigationView.setupWithNavController(navController)

    }

}