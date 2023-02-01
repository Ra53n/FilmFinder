package com.example.filmfinder.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.filmfinder.R
import com.example.filmfinder.presentation.navigation.Navigator
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container
        ) as NavHostFragment?
        navController = navHostFragment?.navController
        navController?.let { controller -> loadKoinModules(module { single { Navigator(controller) } }) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }
}