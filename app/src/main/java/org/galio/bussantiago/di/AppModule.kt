package org.galio.bussantiago.di

import org.galio.bussantiago.common.AppExecutors
import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.domain.LineRepository
import org.koin.dsl.module

val appModule = module {

  // Repositories
  factory { ApiClient() }
  single<LineRepository> { LineRepositoryImpl(apiClient = get(), mapper = LineMapper()) }

  // AppExecutors
  single { AppExecutors() }
}
