package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.domain.interactor.GetLineIncidences
import org.galio.bussantiago.executor.InteractorExecutor

class IncidencesViewModel(
  private val executor: InteractorExecutor,
  private val getLineIncidences: GetLineIncidences
) : ViewModel() {

  private var lineId: Int = 0

  private val _incidences = MutableLiveData<Resource<List<String>>>()

  val incidences: LiveData<Resource<List<String>>>
    get() = _incidences

  init {
    executor.setViewModelScope(viewModelScope)
  }

  fun setArgs(lineId: Int) {
    this.lineId = lineId
  }

  fun loadIncidences() {
    _incidences.value = Resource.loading()
    executor(
      interactor = getLineIncidences,
      request = lineId,
      onSuccess = { incidences ->
        _incidences.value = Resource.success(incidences.map { it.description })
      },
      onError = {
        _incidences.value = Resource.error(it)
      }
    )
  }
}
