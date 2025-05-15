package org.galio.bussantiago.features

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import org.galio.bussantiago.R
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  private val reviewsHelper: ReviewsHelper by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Use WindowCompat to opt into edge-to-edge layout
    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContentView(R.layout.main_activity)

    // Let NavController automatically handle deep links when there is data
    if (intent?.data != null) {
      navController()?.handleDeepLink(intent)
    }

    reviewsHelper.initReviews()

    // Optional: inset handling for padding
    val rootView = findViewById<View>(R.id.navHostFragment)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      // Apply padding to avoid content being hidden behind system bars
      view.setPadding(
        systemBars.left,
        systemBars.top,
        systemBars.right,
        systemBars.bottom
      )
      insets
    }
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
