package org.galio.bussantiago.framework

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory

class ReviewsHelper(context: Context) {

  private val reviewManager = ReviewManagerFactory.create(context)

  private var reviewInfo: ReviewInfo? = null

  // It is necessary pre-cache the ReviewInfo instance,
  // due to it is an asynchronous process that may require some time
  fun initReviews() {
    val requestTask = reviewManager.requestReviewFlow()
    requestTask.addOnCompleteListener { request ->
      if (request.isSuccessful) {
        reviewInfo = request.result
      }
    }
  }

  fun launchReviews(activity: Activity) {
    reviewInfo?.let { reviewManager.launchReviewFlow(activity, it) }
  }
}
