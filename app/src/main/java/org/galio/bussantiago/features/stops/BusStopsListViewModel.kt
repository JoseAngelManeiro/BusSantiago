package org.galio.bussantiago.features.stops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.interactor.GetLineBusStops

class BusStopsListViewModel(
  private val executor: InteractorExecutor,
  private val getLineBusStops: GetLineBusStops
) : ViewModel() {

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
}
