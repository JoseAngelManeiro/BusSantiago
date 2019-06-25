package org.galio.bussantiago.di

import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.domain.LineRepository
import org.koin.dsl.module

val appModule = module {
  single<LineRepository> { LineRepositoryImpl() }
}
