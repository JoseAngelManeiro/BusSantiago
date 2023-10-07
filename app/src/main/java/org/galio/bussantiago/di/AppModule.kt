package org.galio.bussantiago.di

import org.galio.bussantiago.data.BusStopFavoriteRepositoryImpl
import org.galio.bussantiago.data.BusStopRemainingTimesRepositoryImpl
import org.galio.bussantiago.data.LineDetailsRepositoryImpl
import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.data.SearchBusStopRepositoryImpl
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.data.mapper.BusStopMapper
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import org.galio.bussantiago.data.mapper.CoordinatesMapper
import org.galio.bussantiago.data.mapper.IncidenceMapper
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.data.mapper.LineRemainingTimeMapper
import org.galio.bussantiago.data.mapper.LineSearchMapper
import org.galio.bussantiago.data.mapper.RouteMapper
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository
import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.domain.repository.LineRepository
import org.galio.bussantiago.domain.repository.SearchBusStopRepository
import org.galio.bussantiago.executor.AsyncInteractorExecutor
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.framework.FavoriteDataSourceImpl
import org.galio.bussantiago.framework.NetworkHandlerImpl
import org.galio.bussantiago.framework.ReviewsHelper
import org.galio.bussantiago.framework.SettingsPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

  single {
    ApiClient(networkHandler = NetworkHandlerImpl(androidContext()))
  }
  single<FavoriteDataSource> {
    FavoriteDataSourceImpl(androidContext())
  }
  single {
    SettingsPreferences(androidContext())
  }
  single {
    ReviewsHelper(androidContext())
  }

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
  factory { LineSearchMapper() }
  factory { BusStopSearchMapper(coordinatesMapper = get(), lineSearchMapper = get()) }

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
  single<BusStopFavoriteRepository> {
    BusStopFavoriteRepositoryImpl(favoriteDataSource = get())
  }
  single<SearchBusStopRepository> {
    SearchBusStopRepositoryImpl(apiClient = get(), mapper = get())
  }

  // Executor
  factory<InteractorExecutor> {
    AsyncInteractorExecutor()
  }
}
