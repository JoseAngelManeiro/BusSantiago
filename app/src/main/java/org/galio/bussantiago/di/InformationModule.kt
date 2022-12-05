package org.galio.bussantiago.di

import org.galio.bussantiago.domain.interactor.GetLineInformation
import org.galio.bussantiago.features.information.InformationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val informationModule = module {
  factory {
    GetLineInformation(
      lineDetailsRepository = get()
    )
  }
  viewModel {
    InformationViewModel(
      executor = get(),
      getLineInformation = get()
    )
  }
}
