package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.executor.UseCaseExecutor

class IncidencesViewModel(
  private val executor: UseCaseExecutor,
  private val getLineIncidences: GetLineIncidences
) : BaseViewModel(executor) {

  private val _incidences = MutableLiveData<Resource<List<Incidence>>>()

  val incidences: LiveData<Resource<List<Incidence>>>
    get() = _incidences

  fun loadIncidences(lineId: Int) {
    _incidences.value = Resource.loading()
    executor(
      useCase = { getLineIncidences(lineId) },
      onSuccess = { incidences ->
        _incidences.value = Resource.success(
          incidences.sortedByDescending { it.startDate }
        )
      },
      onError = {
        _incidences.value = Resource.error(it)
      }
    )
  }
}
