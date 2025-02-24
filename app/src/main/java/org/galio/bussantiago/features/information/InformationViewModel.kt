package org.galio.bussantiago.features.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.domain.interactor.GetLineInformation
import org.galio.bussantiago.executor.InteractorExecutor

class InformationViewModel(
  private val executor: InteractorExecutor,
  private val getLineInformation: GetLineInformation
) : BaseViewModel(executor) {

  private val _information = MutableLiveData<Resource<String>>()

  val information: LiveData<Resource<String>>
    get() = _information

  fun loadLineInformation(lineId: Int) {
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
