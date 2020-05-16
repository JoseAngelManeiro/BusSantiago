package org.galio.bussantiago

import android.app.Application
import org.galio.bussantiago.di.appModule
import org.galio.bussantiago.di.informationModule
import org.galio.bussantiago.di.linesModule
import org.galio.bussantiago.di.menuModule
import org.galio.bussantiago.di.incidencesModule
import org.galio.bussantiago.di.busStopsModule
import org.galio.bussantiago.di.favoritesModule
import org.galio.bussantiago.di.timesModule
import org.galio.bussantiago.di.widgetModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BusSantiagoApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger()
      androidContext(this@BusSantiagoApp)
      modules(listOf(
        appModule,
        linesModule,
        menuModule,
        informationModule,
        incidencesModule,
        busStopsModule,
        timesModule,
        favoritesModule,
        widgetModule
      ))
    }
  }
}
