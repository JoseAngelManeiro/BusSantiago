package org.galio.bussantiago.di

import org.galio.bussantiago.features.menu.MenuFactory
import org.galio.bussantiago.features.menu.MenuTextUtils
import org.galio.bussantiago.features.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {

  factory { MenuTextUtils() }

  viewModel {
    MenuViewModel(
      executor = get(),
      getLineDetails = get(),
      menuFactory = MenuFactory()
    )
  }
}
