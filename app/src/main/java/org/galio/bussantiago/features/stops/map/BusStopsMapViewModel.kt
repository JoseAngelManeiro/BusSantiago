package org.galio.bussantiago.features.stops.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator

class BusStopsMapViewModel(
  private val executor: InteractorExecutor,
  private val getLineDetails: GetLineDetails,
  private val lineMapModelFactory: LineMapModelFactory,
  private val navigator: Navigator
) : BaseViewModel(executor) {

  private val _lineMapModel = MutableLiveData<Resource<LineMapModel>>()

  val lineMapModel: LiveData<Resource<LineMapModel>>
    get() = _lineMapModel

  fun load(busStopsArgs: BusStopsArgs) {
    executor(
      interactor = getLineDetails,
      request = busStopsArgs.lineId,
      onSuccess = { lineDetails ->
        _lineMapModel.value = Resource.success(
          lineMapModelFactory.createLineMapModelFactory(
            busStopsArgs.routeName, lineDetails
          )
        )
      },
      onError = {
        _lineMapModel.value = Resource.error(it)
      }
    )
  }

  fun onInfoWindowClick(markerTitle: String?, markerDescription: String?) {
    if (markerTitle != null && markerDescription != null) {
      navigator.navigate(NavScreen.Times(BusStopModel(markerTitle, markerDescription)))
    }
  }
}
