package org.galio.bussantiago.di

import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.executor.AsyncInteractorExecutor
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.framework.FavoriteDataSourceImpl
import org.galio.bussantiago.framework.NetworkHandlerImpl
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

  single {
    ApiClient(networkHandler = NetworkHandlerImpl(androidContext()))
  }
  single<FavoriteDataSource> {
    FavoriteDataSourceImpl(androidContext())
  }
  single {
    ReviewsHelper(androidContext())
  }

  // Executor
  factory<InteractorExecutor> {
    AsyncInteractorExecutor()
  }
}
