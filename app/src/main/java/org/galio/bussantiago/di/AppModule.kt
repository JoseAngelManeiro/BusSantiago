package org.galio.bussantiago.di

import org.galio.bussantiago.executor.AsyncInteractorExecutor
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

  single {
    ReviewsHelper(androidContext())
  }

  // Executor
  factory<InteractorExecutor> {
    AsyncInteractorExecutor()
  }
}
