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
import org.galio.bussantiago.framework.analytics.AnalyticsEvents
import org.galio.bussantiago.framework.analytics.AnalyticsParams
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
import org.galio.bussantiago.navigation.NavScreen

class BusStopsMapViewModel(
  private val executor: UseCaseExecutor,
  private val getLineDetails: GetLineDetails,
  private val lineMapModelFactory: LineMapModelFactory,
  private val analyticsTracker: AnalyticsTracker
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
      val busStopModel = BusStopModel(markerTitle, markerDescription)

      analyticsTracker.trackEvent(
        AnalyticsEvents.SELECT_STOP,
        mapOf(
          AnalyticsParams.ORIGIN to Screens.LINE_STOPS_MAP,
          AnalyticsParams.STOP_CODE to busStopModel.code,
          AnalyticsParams.STOP_NAME to busStopModel.name
        )
      )

      _navigationEvent.value = NavScreen.Times(busStopModel)
    }
  }
}
