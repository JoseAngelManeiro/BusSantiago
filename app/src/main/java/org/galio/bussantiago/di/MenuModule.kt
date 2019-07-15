package org.galio.bussantiago.di

import org.galio.bussantiago.menu.GetLineDetails
import org.galio.bussantiago.menu.MenuViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
  factory { GetLineDetails(lineDetailsRepository = get()) }
  viewModel { MenuViewModel(executor = get(), getLineDetails = get()) }
}
