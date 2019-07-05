package org.galio.bussantiago.di

import org.galio.bussantiago.common.executor.AsyncInteractorExecutor
import org.galio.bussantiago.common.executor.BackgroundRunner
import org.galio.bussantiago.common.executor.InteractorExecutor
import org.galio.bussantiago.common.executor.MainRunner
import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.api.NetworkHandler
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.domain.LineRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

  // Repositories
  single { ApiClient(networkHandler = NetworkHandler(androidContext())) }
  single<LineRepository> { LineRepositoryImpl(apiClient = get(), mapper = LineMapper()) }

  // Executor
  single<InteractorExecutor> {
    AsyncInteractorExecutor(runOnBgThread = BackgroundRunner(), runOnMainThread = MainRunner())
  }
}
