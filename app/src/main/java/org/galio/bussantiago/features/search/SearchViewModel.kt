package org.galio.bussantiago.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.interactor.SearchBusStop
import org.galio.bussantiago.executor.InteractorExecutor

class SearchViewModel(
  private val executor: InteractorExecutor,
  private val searchBusStop: SearchBusStop
) : BaseViewModel(executor) {

  private val _busStopModel = MutableLiveData<Resource<BusStopModel>>()

  val busStopModel: LiveData<Resource<BusStopModel>>
    get() = _busStopModel

  fun search(busStopCode: Int) {
    _busStopModel.value = Resource.loading()
    executor(
      interactor = searchBusStop,
      request = busStopCode.toString(),
      onSuccess = {
        _busStopModel.value = Resource.success(BusStopModel(code = it.code, name = it.name))
      },
      onError = {
        _busStopModel.value = Resource.error(it)
      }
    )
  }
}
