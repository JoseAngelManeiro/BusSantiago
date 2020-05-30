package org.galio.bussantiago.features.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.domain.interactor.GetLineInformation

class InformationViewModel(
  private val executor: InteractorExecutor,
  private val getLineInformation: GetLineInformation
) : ViewModel() {

  private var lineId: Int = 0

  private val _information = MutableLiveData<Resource<String>>()

  val information: LiveData<Resource<String>>
    get() = _information

  fun setArgs(lineId: Int) {
    this.lineId = lineId
  }

  fun loadLineInformation() {
    _information.value = Resource.loading()
    executor(
      interactor = getLineInformation,
      request = lineId,
      onSuccess = {
        _information.value = Resource.success(it)
      },
      onError = {
        _information.value = Resource.error(it)
      }
    )
  }
}
