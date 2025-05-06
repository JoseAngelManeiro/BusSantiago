package org.galio.bussantiago.di

import androidx.fragment.app.Fragment
import org.galio.bussantiago.features.lines.LinesViewModel
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val linesModule = module {

  viewModel { (fragment: Fragment) ->
    LinesViewModel(
      executor = get(),
      getLines = get(),
      navigator = Navigator(fragment)
    )
  }
}
