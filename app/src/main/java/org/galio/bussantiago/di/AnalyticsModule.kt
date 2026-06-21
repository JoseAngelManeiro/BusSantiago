package org.galio.bussantiago.di

import com.google.firebase.analytics.FirebaseAnalytics
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.FirebaseAnalyticsTracker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val analyticsModule = module {
  single { FirebaseAnalytics.getInstance(androidContext()) }
  single<AnalyticsTracker> { FirebaseAnalyticsTracker(get()) }
}
