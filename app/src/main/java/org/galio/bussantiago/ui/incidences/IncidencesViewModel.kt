package org.galio.bussantiago.ui.incidences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class IncidencesViewModel(
  private val executor: InteractorExecutor,
  private val getLineIncidences: GetLineIncidences
) : ViewModel() {

  private val _incidencesModel = MutableLiveData<Resource<List<String>>>()

  val incidencesModel: LiveData<Resource<List<String>>>
    get() = _incidencesModel

  fun loadIncidences(id: Int) {
    _incidencesModel.value = Resource.loading()
    executor(
      interactor = getLineIncidences,
      request = id,
      onSuccess = { incidences ->
        _incidencesModel.value = Resource.success(incidences.map { it.description })
      },
      onError = {
        _incidencesModel.value = Resource.error(it)
      }
    )
  }
}
