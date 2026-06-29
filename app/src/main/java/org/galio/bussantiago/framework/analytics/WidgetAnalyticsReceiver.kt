package org.galio.bussantiago.framework.analytics

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * BroadcastReceiver responsible for handling analytics events originating from the widget module.
 *
 * Since the `widget` module cannot depend directly on the `app` module (to avoid circular dependencies),
 * it cannot directly inject the [AnalyticsTracker]. Instead, the widget sends a local broadcast
 * containing the event details, which this receiver intercepts. Being in the `app` module, this
 * receiver can successfully resolve and use the [AnalyticsTracker] via Koin.
 */
class WidgetAnalyticsReceiver : BroadcastReceiver(), KoinComponent {

  private val analyticsTracker: AnalyticsTracker by inject()

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == "org.galio.bussantiago.WIDGET_ANALYTICS_EVENT") {
      val eventName = intent.getStringExtra("eventName") ?: return
      analyticsTracker.trackEvent(eventName, mapOf("origin" to "widget"))
    }
  }
}
