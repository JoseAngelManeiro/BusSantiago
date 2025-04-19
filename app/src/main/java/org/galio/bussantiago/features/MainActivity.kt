package org.galio.bussantiago.features

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import org.galio.bussantiago.R
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  private val reviewsHelper: ReviewsHelper by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    // Let NavController automatically handle deep links when there is data
    if (intent?.data != null) {
      navController()?.handleDeepLink(intent)
    }

    reviewsHelper.initReviews()
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    navController()?.handleDeepLink(intent)
  }

  override fun onSupportNavigateUp() = navController()?.navigateUp() ?: false

  private fun navController(): NavController? {
    return try {
      findNavController(this, R.id.navHostFragment)
    } catch (e: IllegalStateException) {
      null
    }
  }
}
