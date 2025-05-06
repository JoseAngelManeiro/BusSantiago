package org.galio.bussantiago.di

import androidx.fragment.app.Fragment
import org.galio.bussantiago.features.stops.map.BusStopsMapViewModel
import org.galio.bussantiago.features.stops.map.LineMapModelFactory
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busStopsMapModule = module {

  factory { LineMapModelFactory() }

  viewModel { (fragment: Fragment) ->
    BusStopsMapViewModel(
      executor = get(),
      getLineDetails = get(),
      lineMapModelFactory = get(),
      navigator = Navigator(fragment)
    )
  }
}
