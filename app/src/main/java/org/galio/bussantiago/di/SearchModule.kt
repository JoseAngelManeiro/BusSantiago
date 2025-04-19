package org.galio.bussantiago.di

import org.galio.bussantiago.features.search.SearchUtils
import org.galio.bussantiago.features.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
  factory { SearchUtils() }
  viewModel { SearchViewModel(executor = get(), searchAllBusStops = get()) }
}
