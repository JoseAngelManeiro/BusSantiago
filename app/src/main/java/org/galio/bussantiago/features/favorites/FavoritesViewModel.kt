package org.galio.bussantiago.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.navigation.NavScreen

class FavoritesViewModel(
  private val executor: UseCaseExecutor,
  private val getBusStopFavorites: GetBusStopFavorites
) : BaseViewModel(executor) {

  private val _favoriteModels = MutableLiveData<Resource<List<BusStopFavorite>>>()
  private val _navigationEvent = SingleLiveEvent<NavScreen>()

  val favoriteModels: LiveData<Resource<List<BusStopFavorite>>>
    get() = _favoriteModels

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  fun loadFavorites() {
    executor(
      useCase = { getBusStopFavorites() },
      onSuccess = { _favoriteModels.value = Resource.success(it) },
      onError = { _favoriteModels.value = Resource.error(it) }
    )
  }

  fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
    val busStopModel = BusStopModel(busStopFavorite.code, busStopFavorite.name)
    _navigationEvent.value = NavScreen.Times(busStopModel)
  }
}
