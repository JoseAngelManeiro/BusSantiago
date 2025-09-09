package org.galio.bussantiago.di

import org.galio.bussantiago.executor.AsyncUseCaseExecutor
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

  single {
    ReviewsHelper(androidContext())
  }

  // Executor
  factory<UseCaseExecutor> {
    AsyncUseCaseExecutor()
  }
}
