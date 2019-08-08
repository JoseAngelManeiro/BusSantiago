package org.galio.bussantiago.common.di

import org.galio.bussantiago.common.executor.AsyncInteractorExecutor
import org.galio.bussantiago.common.executor.BackgroundRunner
import org.galio.bussantiago.common.executor.InteractorExecutor
import org.galio.bussantiago.common.executor.MainRunner
import org.galio.bussantiago.data.BusStopRemainingTimesRepositoryImpl
import org.galio.bussantiago.data.LineDetailsRepositoryImpl
import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.api.NetworkHandler
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.mapper.BusStopMapper
import org.galio.bussantiago.data.mapper.CoordinatesMapper
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.data.mapper.RouteMapper
import org.galio.bussantiago.data.mapper.IncidenceMapper
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.data.mapper.LineRemainingTimeMapper
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper
import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.domain.repository.LineRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

  single { ApiClient(networkHandler = NetworkHandler(androidContext())) }

  // Mappers factories
  factory { LineMapper() }
  factory { CoordinatesMapper() }
  factory { BusStopMapper(coordinatesMapper = get()) }
  factory { RouteMapper(busStopMapper = get()) }
  factory { IncidenceMapper() }
  factory { LineDetailsMapper(routeMapper = get(), incidenceMapper = get()) }
  factory { LineRemainingTimeMapper() }
  factory {
    BusStopRemainingTimesMapper(coordinatesMapper = get(), lineRemainingTimeMapper = get())
  }

  // Cache factories
  factory { LineDetailsCache() }
  factory { LineCache() }

  // Repositories
  single<LineRepository> {
    LineRepositoryImpl(apiClient = get(), mapper = get(), cache = get())
  }
  single<LineDetailsRepository> {
    LineDetailsRepositoryImpl(apiClient = get(), mapper = get(), cache = get())
  }
  single<BusStopRemainingTimesRepository> {
    BusStopRemainingTimesRepositoryImpl(apiClient = get(), mapper = get())
  }

  // Executor
  single<InteractorExecutor> {
    AsyncInteractorExecutor(runOnBgThread = BackgroundRunner(), runOnMainThread = MainRunner())
  }
}
