package org.galio.bussantiago.di

import androidx.fragment.app.Fragment
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.features.stops.list.BusStopsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busStopsListModule = module {

  viewModel { (fragment: Fragment) ->
    BusStopsListViewModel(
      executor = get(),
      getLineBusStops = get(),
      navigator = Navigator(fragment)
    )
  }
}
