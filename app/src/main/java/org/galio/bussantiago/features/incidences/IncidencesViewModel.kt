package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.domain.interactor.GetLineIncidences

class IncidencesViewModel(
  private val executor: InteractorExecutor,
  private val getLineIncidences: GetLineIncidences
) : ViewModel() {

  private val _incidences = MutableLiveData<Resource<List<String>>>()

  val incidences: LiveData<Resource<List<String>>>
    get() = _incidences

  fun loadIncidences(id: Int) {
    _incidences.value = Resource.loading()
    executor(
      interactor = getLineIncidences,
      request = id,
      onSuccess = { incidences ->
        _incidences.value = Resource.success(incidences.map { it.description })
      },
      onError = {
        _incidences.value = Resource.error(it)
      }
    )
  }
}
