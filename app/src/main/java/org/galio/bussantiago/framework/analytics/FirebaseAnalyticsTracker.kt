package org.galio.bussantiago.framework.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsTracker(
  private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

  override fun trackScreen(screenName: String) {
    val bundle = Bundle().apply {
      putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
      putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
    }
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
  }

  override fun trackEvent(eventName: String, params: Map<String, Any?>) {
    val bundle = Bundle().apply {
      params.forEach { (key, value) ->
        when (value) {
          is String -> putString(key, value)
          is Int -> putLong(key, value.toLong())
          is Long -> putLong(key, value)
          is Double -> putDouble(key, value)
          is Float -> putDouble(key, value.toDouble())
          is Boolean -> putString(key, value.toString()) // Firebase prefers strings/numbers for boolean logic
          null -> { /* Skip or handle nulls */ }
        }
      }
    }
    firebaseAnalytics.logEvent(eventName, bundle)
  }
}
