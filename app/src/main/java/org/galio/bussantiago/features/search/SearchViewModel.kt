package org.galio.bussantiago.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.domain.interactor.SearchBusStop

class SearchViewModel(
  private val executor: InteractorExecutor,
  private val searchBusStop: SearchBusStop
) : ViewModel() {

  private val _busStopModel = MutableLiveData<Resource<BusStopModel?>>()

  val busStopModel: LiveData<Resource<BusStopModel?>>
    get() = _busStopModel

  fun search(busStopCode: String) {
    _busStopModel.value = Resource.loading()
    executor(
      interactor = searchBusStop,
      request = busStopCode,
      onSuccess = {
        _busStopModel.value = Resource.success(
          it?.let { BusStopModel(code = it.code, name = it.name) }
        )
      },
      onError = {
        _busStopModel.value = Resource.error(it)
      }
    )
  }
}
