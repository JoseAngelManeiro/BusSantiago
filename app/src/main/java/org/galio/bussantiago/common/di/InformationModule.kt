package org.galio.bussantiago.common.di

import org.galio.bussantiago.ui.information.GetLineInformation
import org.galio.bussantiago.ui.information.InformationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val informationModule = module {
  factory { GetLineInformation(lineDetailsRepository = get()) }
  viewModel {
    InformationViewModel(
      executor = get(),
      getLineInformation = get()
    )
  }
}
