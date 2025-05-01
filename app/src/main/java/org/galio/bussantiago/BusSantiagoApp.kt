package org.galio.bussantiago

import android.app.Application
import org.galio.bussantiago.di.appModule
import org.galio.bussantiago.di.busStopsListModule
import org.galio.bussantiago.di.busStopsMapModule
import org.galio.bussantiago.di.coreModule
import org.galio.bussantiago.di.favoritesModule
import org.galio.bussantiago.di.incidencesModule
import org.galio.bussantiago.di.informationModule
import org.galio.bussantiago.di.linesModule
import org.galio.bussantiago.di.menuModule
import org.galio.bussantiago.di.searchModule
import org.galio.bussantiago.di.timesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BusSantiagoApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@BusSantiagoApp)
      modules(
        listOf(
          appModule,
          coreModule,
          linesModule,
          menuModule,
          informationModule,
          incidencesModule,
          busStopsMapModule,
          busStopsListModule,
          timesModule,
          searchModule,
          favoritesModule
        )
      )
    }
  }
}
