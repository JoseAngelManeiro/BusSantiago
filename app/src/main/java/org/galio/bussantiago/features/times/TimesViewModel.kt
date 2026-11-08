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
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.framework.analytics.AnalyticsEvents
import org.galio.bussantiago.framework.analytics.AnalyticsParams
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.TimeFormatter
import org.galio.bussantiago.shared.TimesFactory

class TimesViewModel(
  private val executor: UseCaseExecutor,
  private val getBusStopRemainingTimes: GetBusStopRemainingTimes,
  private val validateIfBusStopIsFavorite: ValidateIfBusStopIsFavorite,
  private val addBusStopFavorite: AddBusStopFavorite,
  private val removeBusStopFavorite: RemoveBusStopFavorite,
  private val timesFactory: TimesFactory,
  private val analyticsTracker: AnalyticsTracker,
  private val timeFormatter: TimeFormatter
) : BaseViewModel(executor) {

  private lateinit var busStopCode: String
  private lateinit var busStopName: String

  private val _lineRemainingTimeModels = MutableLiveData<Resource<List<LineRemainingTimeModel>>>()
  private val _isFavorite = MutableLiveData<Boolean>()
  private val _lastUpdatedTime = MutableLiveData<String>()

  val lineRemainingTimeModels: LiveData<Resource<List<LineRemainingTimeModel>>>
    get() = _lineRemainingTimeModels
  val isFavorite: LiveData<Boolean>
    get() = _isFavorite
  val lastUpdatedTime: LiveData<String>
    get() = _lastUpdatedTime

  fun setArgs(busStopCode: String, busStopName: String) {
    this.busStopCode = busStopCode
    this.busStopName = busStopName
  }

  fun init() {
    analyticsTracker.trackScreen(Screens.TIMES)
    loadTimes()
    validateBusStop()
  }

  fun loadTimes() {
    _lineRemainingTimeModels.value = Resource.loading()
    executor(
      useCase = { getBusStopRemainingTimes(busStopCode) },
      onSuccess = { times ->
        val models = timesFactory.createLineRemainingTimeModels(times)
        _lastUpdatedTime.value = timeFormatter.formatTime()
        _lineRemainingTimeModels.value = Resource.success(models)
      },
      onError = {
        _lineRemainingTimeModels.value = Resource.error(it)
      }
    )
  }

  fun validateBusStop() {
    executor(useCase = { validateIfBusStopIsFavorite(busStopCode) }) {
      _isFavorite.value = it
    }
  }

  fun changeFavoriteState() {
    _isFavorite.value?.let {
      val nextStatus = !it
      _isFavorite.value = nextStatus
      analyticsTracker.trackEvent(
        AnalyticsEvents.TOGGLE_FAVORITE,
        mapOf(
          AnalyticsParams.STOP_CODE to busStopCode,
          AnalyticsParams.STOP_NAME to busStopName,
          AnalyticsParams.IS_FAVORITE to nextStatus
        )
      )
      if (nextStatus) {
        executor(useCase = { addBusStopFavorite(BusStopFavorite(busStopCode, busStopName)) })
      } else {
        executor(useCase = { removeBusStopFavorite(BusStopFavorite(busStopCode, busStopName)) })
      }
    }
  }
}
