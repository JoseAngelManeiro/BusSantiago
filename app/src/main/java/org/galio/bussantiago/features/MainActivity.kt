package org.galio.bussantiago.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import org.galio.bussantiago.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
  }

  override fun onSupportNavigateUp() =
    findNavController(this, R.id.navHostFragment).navigateUp()
}
