package org.galio.bussantiago.di

import org.galio.bussantiago.data.BusStopFavoriteRepositoryImpl
import org.galio.bussantiago.data.BusStopRemainingTimesRepositoryImpl
import org.galio.bussantiago.data.LineDetailsRepositoryImpl
import org.galio.bussantiago.data.LineRepositoryImpl
import org.galio.bussantiago.data.SearchBusStopRepositoryImpl
import org.galio.bussantiago.data.cache.BusStopSearchCache
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.cache.LineDetailsCache
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
import org.galio.bussantiago.domain.interactor.AddBusStopFavorite
import org.galio.bussantiago.domain.interactor.GetBusStopFavorites
import org.galio.bussantiago.domain.interactor.GetBusStopRemainingTimes
import org.galio.bussantiago.domain.interactor.GetLineBusStops
import org.galio.bussantiago.domain.interactor.GetLineDetails
import org.galio.bussantiago.domain.interactor.GetLineIncidences
import org.galio.bussantiago.domain.interactor.GetLineInformation
import org.galio.bussantiago.domain.interactor.GetLines
import org.galio.bussantiago.domain.interactor.RemoveBusStopFavorite
import org.galio.bussantiago.domain.interactor.SearchAllBusStops
import org.galio.bussantiago.domain.interactor.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository
import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.domain.repository.LineRepository
import org.galio.bussantiago.domain.repository.SearchBusStopRepository
import org.koin.dsl.module

val coreModule = module {

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
  factory { BusStopSearchCache() }

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
    SearchBusStopRepositoryImpl(apiClient = get(), mapper = get(), cache = get())
  }

  // UseCases
  factory { GetLineBusStops(lineDetailsRepository = get()) }
  factory { GetBusStopFavorites(busStopFavoriteRepository = get()) }
  factory { GetLineIncidences(lineDetailsRepository = get()) }
  factory { GetLineInformation(lineDetailsRepository = get()) }
  factory { GetLines(lineRepository = get()) }
  factory { GetLineDetails(lineDetailsRepository = get()) }
  factory { SearchAllBusStops(searchBusStopRepository = get()) }
  factory { GetBusStopRemainingTimes(busStopRemainingTimesRepository = get()) }
  factory { ValidateIfBusStopIsFavorite(busStopFavoriteRepository = get()) }
  factory { AddBusStopFavorite(busStopFavoriteRepository = get()) }
  factory { RemoveBusStopFavorite(busStopFavoriteRepository = get()) }
}
