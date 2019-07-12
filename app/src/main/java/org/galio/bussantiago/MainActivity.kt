package org.galio.bussantiago

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.main_activity.*
import org.galio.bussantiago.common.DialogFragmentNavigator

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    setUpNavigationGraph()
  }

  private fun setUpNavigationGraph() {
    val destination = DialogFragmentNavigator(navHostFragment.childFragmentManager)
    navHostFragment.findNavController().navigatorProvider.addNavigator(destination)

    val inflater = navHostFragment.findNavController().navInflater
    val graph = inflater.inflate(R.navigation.nav_graph)
    navHostFragment.findNavController().graph = graph
  }

  override fun onSupportNavigateUp() =
    findNavController(this, R.id.navHostFragment).navigateUp()
}
