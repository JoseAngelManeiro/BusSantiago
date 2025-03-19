package org.galio.bussantiago.di

import org.galio.bussantiago.core.AddBusStopFavorite
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.core.GetLines
import org.galio.bussantiago.core.RemoveBusStopFavorite
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.BusStopSearchCache
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.data.local.FavoriteDataSourceImpl
import org.galio.bussantiago.data.mapper.BusStopMapper
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import org.galio.bussantiago.data.mapper.CoordinatesMapper
import org.galio.bussantiago.data.mapper.DateMapper
import org.galio.bussantiago.data.mapper.IncidenceMapper
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.data.mapper.LineRemainingTimeMapper
import org.galio.bussantiago.data.mapper.LineSearchMapper
import org.galio.bussantiago.data.mapper.RouteMapper
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.data.repository.BusStopRemainingTimesRepository
import org.galio.bussantiago.data.repository.LineDetailsRepository
import org.galio.bussantiago.data.repository.LineRepository
import org.galio.bussantiago.data.repository.SearchBusStopRepository
import org.galio.bussantiago.domain.AddBusStopFavoriteImpl
import org.galio.bussantiago.domain.GetBusStopFavoritesImpl
import org.galio.bussantiago.domain.GetBusStopRemainingTimesImpl
import org.galio.bussantiago.domain.GetLineBusStopsImpl
import org.galio.bussantiago.domain.GetLineDetailsImpl
import org.galio.bussantiago.domain.GetLineIncidencesImpl
import org.galio.bussantiago.domain.GetLineInformationImpl
import org.galio.bussantiago.domain.GetLinesImpl
import org.galio.bussantiago.domain.RemoveBusStopFavoriteImpl
import org.galio.bussantiago.domain.SearchAllBusStopsImpl
import org.galio.bussantiago.domain.ValidateIfBusStopIsFavoriteImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {

  single {
    ApiClient()
  }
  single<FavoriteDataSource> {
    FavoriteDataSourceImpl(androidContext())
  }

  // Mappers factories
  factory { DateMapper() }
  factory { LineMapper() }
  factory { CoordinatesMapper() }
  factory { BusStopMapper(coordinatesMapper = get()) }
  factory { RouteMapper(busStopMapper = get()) }
  factory { IncidenceMapper(dateMapper = get()) }
  factory { LineDetailsMapper(routeMapper = get(), incidenceMapper = get()) }
  factory { LineRemainingTimeMapper(dateMapper = get()) }
  factory {
    BusStopRemainingTimesMapper(coordinatesMapper = get(), lineRemainingTimeMapper = get())
  }
  factory { LineSearchMapper() }
  factory { BusStopSearchMapper(coordinatesMapper = get(), lineSearchMapper = get()) }

  // Cache factories
  factory { LineDetailsCache() }
  factory { LineCache() }
  factory { BusStopSearchCache() }

  // Repositories
  single {
    LineRepository(apiClient = get(), mapper = get(), cache = get())
  }
  single {
    LineDetailsRepository(apiClient = get(), mapper = get(), cache = get())
  }
  single {
    BusStopRemainingTimesRepository(apiClient = get(), mapper = get())
  }
  single {
    BusStopFavoriteRepository(favoriteDataSource = get())
  }
  single {
    SearchBusStopRepository(apiClient = get(), mapper = get(), cache = get())
  }

  // UseCases
  factory<GetLineBusStops> {
    GetLineBusStopsImpl(lineDetailsRepository = get())
  }
  factory<GetBusStopFavorites> {
    GetBusStopFavoritesImpl(busStopFavoriteRepository = get())
  }
  factory<GetLineIncidences> {
    GetLineIncidencesImpl(lineDetailsRepository = get())
  }
  factory<GetLineInformation> {
    GetLineInformationImpl(lineDetailsRepository = get())
  }
  factory<GetLines> {
    GetLinesImpl(lineRepository = get())
  }
  factory<GetLineDetails> {
    GetLineDetailsImpl(lineDetailsRepository = get())
  }
  factory<SearchAllBusStops> {
    SearchAllBusStopsImpl(searchBusStopRepository = get())
  }
  factory<GetBusStopRemainingTimes> {
    GetBusStopRemainingTimesImpl(busStopRemainingTimesRepository = get())
  }
  factory<ValidateIfBusStopIsFavorite> {
    ValidateIfBusStopIsFavoriteImpl(busStopFavoriteRepository = get())
  }
  factory<AddBusStopFavorite> {
    AddBusStopFavoriteImpl(busStopFavoriteRepository = get())
  }
  factory<RemoveBusStopFavorite> {
    RemoveBusStopFavoriteImpl(busStopFavoriteRepository = get())
  }
}
