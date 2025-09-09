package org.galio.bussantiago.di

import org.galio.bussantiago.features.information.InformationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val informationModule = module {
  viewModel {
    InformationViewModel(
      executor = get(),
      getLineInformation = get()
    )
  }
}
