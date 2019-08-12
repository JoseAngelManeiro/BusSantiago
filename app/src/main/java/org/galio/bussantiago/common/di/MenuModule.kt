package org.galio.bussantiago.common.di

import org.galio.bussantiago.features.menu.GetLineDetails
import org.galio.bussantiago.features.menu.MenuFactory
import org.galio.bussantiago.features.menu.MenuViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
  factory { GetLineDetails(lineDetailsRepository = get()) }
  viewModel {
    MenuViewModel(
      executor = get(),
      getLineDetails = get(),
      menuFactory = MenuFactory()
    )
  }
}
