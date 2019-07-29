package org.galio.bussantiago.common.di

import org.galio.bussantiago.ui.menu.GetLineDetails
import org.galio.bussantiago.ui.menu.MenuFactory
import org.galio.bussantiago.ui.menu.MenuViewModel
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
