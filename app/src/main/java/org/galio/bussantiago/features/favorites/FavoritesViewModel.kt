package org.galio.bussantiago.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.domain.interactor.GetBusStopFavorites
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.executor.InteractorExecutor

class FavoritesViewModel(
  private val executor: InteractorExecutor,
  private val getBusStopFavorites: GetBusStopFavorites
) : BaseViewModel(executor) {

  private val _favoriteModels = MutableLiveData<Resource<List<BusStopFavorite>>>()

  val favoriteModels: LiveData<Resource<List<BusStopFavorite>>>
    get() = _favoriteModels

  fun loadFavorites() {
    executor(
      interactor = getBusStopFavorites,
      request = Unit,
      onSuccess = { _favoriteModels.value = Resource.success(it) },
      onError = { _favoriteModels.value = Resource.error(it) }
    )
  }
}
