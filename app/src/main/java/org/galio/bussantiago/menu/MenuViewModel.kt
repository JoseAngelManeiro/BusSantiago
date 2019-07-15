package org.galio.bussantiago.menu

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor
import org.galio.bussantiago.domain.model.LineDetails

class MenuViewModel(
  private val executor: InteractorExecutor,
  private val getLineDetails: GetLineDetails
) : ViewModel() {

  private val _lineDetails = MutableLiveData<Resource<LineDetails>>()

  val lineDetails: LiveData<Resource<LineDetails>>
    get() = _lineDetails

  fun loadLineDetails(id: Int) {
    _lineDetails.value = Resource.loading()
    executor(
      interactor = getLineDetails,
      request = id,
      onSuccess = {
        _lineDetails.value = Resource.success(it)
      },
      onError = {
        _lineDetails.value = Resource.error(it)
      }
    )
  }
}
