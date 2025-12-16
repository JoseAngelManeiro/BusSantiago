package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.navigation.NavScreen

class IncidencesViewModel(
  private val lineId: Int,
  private val executor: UseCaseExecutor,
  private val getLineIncidences: GetLineIncidences
) : BaseViewModel(executor), IncidencesUserInteractions {

  private val _navigationEvent = SingleLiveEvent<NavScreen>()
  private val _incidences = MutableLiveData<Resource<List<Incidence>>>(Resource.loading())

  val incidences: LiveData<Resource<List<Incidence>>>
    get() = _incidences

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  init {
    loadIncidences()
  }

  private fun loadIncidences() {
    _incidences.value = Resource.loading()
    executor(
      useCase = { getLineIncidences(lineId) },
      onSuccess = { incidences ->
        _incidences.value = Resource.success(incidences)
      },
      onError = {
        _incidences.value = Resource.error(it)
      }
    )
  }

  override fun onRetry() {
    loadIncidences()
  }

  override fun onCancel() {
    _navigationEvent.value = NavScreen.Exit
  }
}
