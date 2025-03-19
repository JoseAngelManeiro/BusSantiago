package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.executor.InteractorExecutor

class IncidencesViewModel(
  private val executor: InteractorExecutor,
  private val getLineIncidences: GetLineIncidences
) : BaseViewModel(executor) {

  private val _incidences = MutableLiveData<Resource<List<String>>>()

  val incidences: LiveData<Resource<List<String>>>
    get() = _incidences

  fun loadIncidences(lineId: Int) {
    _incidences.value = Resource.loading()
    executor(
      interactor = getLineIncidences,
      request = lineId,
      onSuccess = { incidences ->
        _incidences.value = Resource.success(
          incidences.map { it.description }.sortedDescending()
        )
      },
      onError = {
        _incidences.value = Resource.error(it)
      }
    )
  }
}
