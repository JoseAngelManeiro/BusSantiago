package org.galio.bussantiago.di

import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.domain.LineRepository
import org.koin.dsl.module

val appModule = module {
  factory { ApiClient() }
  single<LineRepository> { LineRepositoryImpl(get()) }
}
