package org.galio.bussantiago.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService : RemoteViewsService() {

  override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
    return TimesViewsFactory(this.applicationContext, intent)
  }
}
