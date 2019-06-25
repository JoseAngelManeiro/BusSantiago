package org.galio.bussantiago

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

  private lateinit var navigationController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    // Set up NavController
    navigationController = findNavController(R.id.navHostFragment)
    NavigationUI.setupActionBarWithNavController(this, navigationController)
  }

  override fun onSupportNavigateUp() = navigationController.navigateUp()
}
