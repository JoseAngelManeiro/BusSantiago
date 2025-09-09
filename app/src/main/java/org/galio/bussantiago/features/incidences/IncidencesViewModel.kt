package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.executor.UseCaseExecutor

class IncidencesViewModel(
  private val executor: UseCaseExecutor,
  private val getLineIncidences: GetLineIncidences
) : BaseViewModel(executor) {

  private val _incidences = MutableLiveData<Resource<List<String>>>()

  val incidences: LiveData<Resource<List<String>>>
    get() = _incidences

  fun loadIncidences(lineId: Int) {
    _incidences.value = Resource.loading()
    executor(
      useCase = { getLineIncidences(lineId) },
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
