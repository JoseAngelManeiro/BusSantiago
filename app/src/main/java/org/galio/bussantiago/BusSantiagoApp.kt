package org.galio.bussantiago

import android.app.Application
import org.galio.bussantiago.common.di.appModule
import org.galio.bussantiago.common.di.linesModule
import org.galio.bussantiago.common.di.menuModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BusSantiagoApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger()
      androidContext(this@BusSantiagoApp)
      modules(listOf(appModule, linesModule, menuModule))
    }
  }
}
