package org.galio.bussantiago.di

import org.galio.bussantiago.features.menu.MenuFactory
import org.galio.bussantiago.features.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
  viewModel { MenuViewModel(executor = get(), getLineDetails = get(), menuFactory = MenuFactory()) }
}
