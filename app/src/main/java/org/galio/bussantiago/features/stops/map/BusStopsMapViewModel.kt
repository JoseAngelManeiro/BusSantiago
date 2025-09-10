package org.galio.bussantiago.features.stops.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.NavScreen

class BusStopsMapViewModel(
  private val executor: UseCaseExecutor,
  private val getLineDetails: GetLineDetails,
  private val lineMapModelFactory: LineMapModelFactory
) : BaseViewModel(executor) {

  private val _lineMapModel = MutableLiveData<Resource<LineMapModel>>()
  private val _navigationEvent = SingleLiveEvent<NavScreen>()

  val lineMapModel: LiveData<Resource<LineMapModel>>
    get() = _lineMapModel

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  fun load(busStopsArgs: BusStopsArgs) {
    executor(
      useCase = { getLineDetails(busStopsArgs.lineId) },
      onSuccess = { lineDetails ->
        lineMapModelFactory.createLineMapModelFactory(
          routeName = busStopsArgs.routeName,
          lineDetails = lineDetails
        )?.let {
          _lineMapModel.value = Resource.success(it)
        }
      },
      onError = {
        _lineMapModel.value = Resource.error(it)
      }
    )
  }

  fun onInfoWindowClick(markerTitle: String?, markerDescription: String?) {
    if (markerTitle != null && markerDescription != null) {
      _navigationEvent.value = NavScreen.Times(BusStopModel(markerTitle, markerDescription))
    }
  }
}
