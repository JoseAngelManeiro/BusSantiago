package org.galio.bussantiago.features.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.navigation.NavScreen

class InformationViewModel(
  private val lineId: Int,
  private val executor: UseCaseExecutor,
  private val getLineInformation: GetLineInformation
) : BaseViewModel(executor), InformationUserInteractions {

  private val _navigationEvent = SingleLiveEvent<NavScreen>()
  private val _information = MutableLiveData<Resource<String>>(Resource.loading())

  val information: LiveData<Resource<String>>
    get() = _information

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  init {
    loadLineInformation()
  }

  private fun loadLineInformation() {
    _information.value = Resource.loading()
    executor(
      useCase = { getLineInformation(lineId) },
      onSuccess = { _information.value = Resource.success(it) },
      onError = { _information.value = Resource.error(it) }
    )
  }

  override fun onRetry() {
    loadLineInformation()
  }

  override fun onCancel() {
    _navigationEvent.value = NavScreen.Exit
  }
}
