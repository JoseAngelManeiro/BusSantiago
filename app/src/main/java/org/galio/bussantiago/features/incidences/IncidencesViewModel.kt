package org.galio.bussantiago.features.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.domain.interactor.GetLineIncidences
import org.galio.bussantiago.executor.InteractorExecutor

class IncidencesViewModel(
  private val executor: InteractorExecutor,
  private val getLineIncidences: GetLineIncidences
) : BaseViewModel(executor) {

  private var lineId: Int = 0

  private val _incidences = MutableLiveData<Resource<List<String>>>()

  val incidences: LiveData<Resource<List<String>>>
    get() = _incidences

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
