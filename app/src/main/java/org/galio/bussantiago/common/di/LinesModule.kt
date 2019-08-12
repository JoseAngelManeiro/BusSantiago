package org.galio.bussantiago.common.di

import org.galio.bussantiago.features.lines.GetLines
import org.galio.bussantiago.features.lines.LinesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val linesModule = module {
  factory { GetLines(lineRepository = get()) }
  viewModel { LinesViewModel(executor = get(), getLines = get()) }
}
