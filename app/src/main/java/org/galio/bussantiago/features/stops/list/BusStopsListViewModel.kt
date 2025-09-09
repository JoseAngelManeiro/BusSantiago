package org.galio.bussantiago.features.stops.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.NavScreen

class BusStopsListViewModel(
  private val executor: UseCaseExecutor,
  private val getLineBusStops: GetLineBusStops
) : BaseViewModel(executor) {

  private val _busStopModels = MutableLiveData<Resource<List<BusStopModel>>>()
  private val _navigationEvent = SingleLiveEvent<NavScreen>()

  val busStopModels: LiveData<Resource<List<BusStopModel>>>
    get() = _busStopModels

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  fun loadBusStops(busStopsArgs: BusStopsArgs) {
    _busStopModels.value = Resource.loading()
    executor(
      useCase = {
        getLineBusStops(
          GetLineBusStops.Request(lineId = busStopsArgs.lineId, routeName = busStopsArgs.routeName)
        )
      },
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
    _navigationEvent.value = NavScreen.Times(busStopModel)
  }
}
