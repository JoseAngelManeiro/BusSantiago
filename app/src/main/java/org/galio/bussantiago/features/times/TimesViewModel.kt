package org.galio.bussantiago.features.times

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class TimesViewModel(
  private val executor: InteractorExecutor,
  private val getBusStopRemainingTimes: GetBusStopRemainingTimes,
  private val timesFactory: TimesFactory
) : ViewModel() {

  private val _lineRemainingTimeModels = MutableLiveData<Resource<List<LineRemainingTimeModel>>>()

  val lineRemainingTimeModels: LiveData<Resource<List<LineRemainingTimeModel>>>
    get() = _lineRemainingTimeModels

  fun loadRemainingTimes(busStopCode: String) {
    _lineRemainingTimeModels.value = Resource.loading()
    executor(
      interactor = getBusStopRemainingTimes,
      request = busStopCode,
      onSuccess = {
        _lineRemainingTimeModels.value = Resource.success(
          timesFactory.createLineRemainingTimeModels(it)
        )
      },
      onError = {
        _lineRemainingTimeModels.value = Resource.error(it)
      }
    )
  }
}
