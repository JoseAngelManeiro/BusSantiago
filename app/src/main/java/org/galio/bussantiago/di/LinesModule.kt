package org.galio.bussantiago.di

import org.galio.bussantiago.features.lines.LinesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val linesModule = module {

  viewModel {
    LinesViewModel(
      executor = get(),
      getLines = get()
    )
  }
}
