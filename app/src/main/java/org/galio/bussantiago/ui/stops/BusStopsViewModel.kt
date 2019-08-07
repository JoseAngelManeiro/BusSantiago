package org.galio.bussantiago.ui.stops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class BusStopsViewModel(
  private val executor: InteractorExecutor,
  private val getLineBusStops: GetLineBusStops
) : ViewModel() {

  private val _busStopModels = MutableLiveData<Resource<List<BusStopModel>>>()

  val busStopModels: LiveData<Resource<List<BusStopModel>>>
    get() = _busStopModels

  fun loadBusStops(lineId: Int, routeName: String) {
    _busStopModels.value = Resource.loading()
    executor(
      interactor = getLineBusStops,
      request = GetLineBusStops.Request(lineId = lineId, routeName = routeName),
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
}
