package org.galio.bussantiago.di

import org.galio.bussantiago.lines.GetLines
import org.galio.bussantiago.lines.LinesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val linesModule = module {
  factory { GetLines(get()) }
  viewModel { LinesViewModel(get()) }
}
