package org.galio.bussantiago.features.stops.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.NavScreen

class BusStopsListViewModel(
  private val executor: InteractorExecutor,
  private val getLineBusStops: GetLineBusStops,
  private val navigator: Navigator
) : BaseViewModel(executor) {

  private val _busStopModels = MutableLiveData<Resource<List<BusStopModel>>>()

  val busStopModels: LiveData<Resource<List<BusStopModel>>>
    get() = _busStopModels

  fun loadBusStops(busStopsArgs: BusStopsArgs) {
    _busStopModels.value = Resource.loading()
    executor(
      interactor = getLineBusStops,
      request = GetLineBusStops.Request(
        lineId = busStopsArgs.lineId,
        routeName = busStopsArgs.routeName
      ),
      onSuccess = { busStops ->
        _busStopModels.value = Resource.success(
          busStops.map { BusStopModel(code = it.code, name = it.name) }
        )
      },
      onError = {
        _busStopModels.value = Resource.error(it)
      }
    )
  }

  fun onBusStopClick(busStopModel: BusStopModel) {
    navigator.navigate(NavScreen.Times(busStopModel))
  }
}
