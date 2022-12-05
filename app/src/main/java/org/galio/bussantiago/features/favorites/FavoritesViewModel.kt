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

  private val _busStopFavorites = MutableLiveData<Resource<List<BusStopFavorite>>>()

  val busStopFavorites: LiveData<Resource<List<BusStopFavorite>>>
    get() = _busStopFavorites

  fun loadFavorites() {
    _busStopFavorites.value = Resource.loading()
    executor(
      interactor = getBusStopFavorites,
      request = Unit,
      onSuccess = { _busStopFavorites.value = Resource.success(it) },
      onError = { _busStopFavorites.value = Resource.error(it) }
    )
  }
}
