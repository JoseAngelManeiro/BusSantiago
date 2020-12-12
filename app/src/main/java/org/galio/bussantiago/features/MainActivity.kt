package org.galio.bussantiago.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import org.galio.bussantiago.R
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  private val reviewsHelper: ReviewsHelper by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    reviewsHelper.initReviews()
  }

  override fun onSupportNavigateUp() =
    findNavController(this, R.id.navHostFragment).navigateUp()
}
