package com.example.innogeeks_team_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var myNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.mainFrag) as NavHostFragment
        myNavController = navHostFrag.navController
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavBar)
        setupWithNavController(bottomNavBar, myNavController)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainFrag,
                    scannerFragment()
                ) // Replace 'fragmentContainer' with your fragment container ID
                .commit()
        }
    }
}