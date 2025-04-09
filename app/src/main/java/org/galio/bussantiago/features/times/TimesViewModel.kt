package org.galio.bussantiago.features.times

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.AddBusStopFavorite
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.galio.bussantiago.core.RemoveBusStopFavorite
import org.galio.bussantiago.core.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.TimesFactory

class TimesViewModel(
  private val executor: InteractorExecutor,
  private val getBusStopRemainingTimes: GetBusStopRemainingTimes,
  private val validateIfBusStopIsFavorite: ValidateIfBusStopIsFavorite,
  private val addBusStopFavorite: AddBusStopFavorite,
  private val removeBusStopFavorite: RemoveBusStopFavorite,
  private val timesFactory: TimesFactory
) : BaseViewModel(executor) {

  private lateinit var busStopCode: String
  private lateinit var busStopName: String

  private val _lineRemainingTimeModels = MutableLiveData<Resource<List<LineRemainingTimeModel>>>()
  private val _isFavorite = MutableLiveData<Boolean>()

  val lineRemainingTimeModels: LiveData<Resource<List<LineRemainingTimeModel>>>
    get() = _lineRemainingTimeModels
  val isFavorite: LiveData<Boolean>
    get() = _isFavorite

  fun setArgs(busStopCode: String, busStopName: String) {
    this.busStopCode = busStopCode
    this.busStopName = busStopName
  }

  fun loadTimes() {
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

  fun validateBusStop() {
    executor(
      interactor = validateIfBusStopIsFavorite,
      request = busStopCode
    ) {
      _isFavorite.value = it
    }
  }

  fun changeFavoriteState() {
    _isFavorite.value?.let {
      _isFavorite.value = !it
      if (_isFavorite.value!!) {
        executor(
          interactor = addBusStopFavorite,
          request = BusStopFavorite(busStopCode, busStopName)
        )
      } else {
        executor(
          interactor = removeBusStopFavorite,
          request = BusStopFavorite(busStopCode, busStopName)
        )
      }
    }
  }
}
