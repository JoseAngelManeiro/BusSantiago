package org.galio.bussantiago.features.stops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.domain.interactor.GetLineDetails

class BusStopsMapViewModel(
  private val executor: InteractorExecutor,
  private val getLineDetails: GetLineDetails,
  private val lineMapModelFactory: LineMapModelFactory
) : ViewModel() {

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
            busStopsArgs.routeName, lineDetails)
        )
      },
      onError = {
        _lineMapModel.value = Resource.error(it)
      }
    )
  }
}
