package org.galio.bussantiago.di

import org.galio.bussantiago.features.information.InformationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val informationModule = module {
  viewModel { (lineId: Int) ->
    InformationViewModel(
      lineId = lineId,
      executor = get(),
      getLineInformation = get()
    )
  }
}
