package org.galio.bussantiago.di

import org.galio.bussantiago.widget.WidgetPrefsHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val widgetModule = module {
  factory {
    WidgetPrefsHelper(context = androidContext())
  }
}
