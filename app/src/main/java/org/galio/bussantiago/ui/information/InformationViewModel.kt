package org.galio.bussantiago.ui.information

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class InformationViewModel(
  private val executor: InteractorExecutor,
  private val getLineInformation: GetLineInformation
) : ViewModel() {

  private val _informationModel = MutableLiveData<Resource<String>>()

  val informationModel: LiveData<Resource<String>>
    get() = _informationModel

  fun loadLineInformation(id: Int) {
    _informationModel.value = Resource.loading()
    executor(
      interactor = getLineInformation,
      request = id,
      onSuccess = {
        _informationModel.value = Resource.success(it)
      },
      onError = {
        _informationModel.value = Resource.error(it)
      }
    )
  }
}
