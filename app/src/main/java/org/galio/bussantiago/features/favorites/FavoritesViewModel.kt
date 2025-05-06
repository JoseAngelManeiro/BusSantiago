package org.galio.bussantiago.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator

class FavoritesViewModel(
  private val executor: InteractorExecutor,
  private val getBusStopFavorites: GetBusStopFavorites,
  private val navigator: Navigator
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

  fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
    val busStopModel = BusStopModel(busStopFavorite.code, busStopFavorite.name)
    navigator.navigate(NavScreen.Times(busStopModel))
  }
}
