package org.galio.bussantiago.di

import androidx.fragment.app.Fragment
import org.galio.bussantiago.features.search.SearchUtils
import org.galio.bussantiago.features.search.SearchViewModel
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

  factory { SearchUtils() }

  viewModel { (fragment: Fragment) ->
    SearchViewModel(
      executor = get(),
      searchAllBusStops = get(),
      navigator = Navigator(fragment)
    )
  }
}
