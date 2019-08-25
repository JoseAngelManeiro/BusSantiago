package org.galio.bussantiago

import android.app.Application
import org.galio.bussantiago.common.di.appModule
import org.galio.bussantiago.common.di.informationModule
import org.galio.bussantiago.common.di.linesModule
import org.galio.bussantiago.common.di.menuModule
import org.galio.bussantiago.common.di.incidencesModule
import org.galio.bussantiago.common.di.busStopsModule
import org.galio.bussantiago.common.di.favoritesModule
import org.galio.bussantiago.common.di.timesModule
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
        favoritesModule
      ))
    }
  }
}
