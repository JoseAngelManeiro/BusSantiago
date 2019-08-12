package org.galio.bussantiago.features.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class InformationViewModel(
  private val executor: InteractorExecutor,
  private val getLineInformation: GetLineInformation
) : ViewModel() {

  private val _information = MutableLiveData<Resource<String>>()

  val information: LiveData<Resource<String>>
    get() = _information

  fun loadLineInformation(id: Int) {
    _information.value = Resource.loading()
    executor(
      interactor = getLineInformation,
      request = id,
      onSuccess = {
        _information.value = Resource.success(it)
      },
      onError = {
        _information.value = Resource.error(it)
      }
    )
  }
}
