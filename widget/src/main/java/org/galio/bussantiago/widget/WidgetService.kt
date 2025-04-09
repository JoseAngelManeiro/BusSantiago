package org.galio.bussantiago.widget

import android.content.Intent
import android.widget.RemoteViewsService
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.koin.android.ext.android.inject

internal class WidgetService : RemoteViewsService() {

  private val getBusStopRemainingTimes: GetBusStopRemainingTimes by inject()

  override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
    return TimesViewsFactory(
      context = this.applicationContext,
      getBusStopRemainingTimes = getBusStopRemainingTimes,
      intent = intent
    )
  }
}
