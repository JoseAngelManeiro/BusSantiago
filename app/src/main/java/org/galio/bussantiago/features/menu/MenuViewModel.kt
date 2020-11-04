package org.galio.bussantiago.features.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.domain.interactor.GetLineDetails

class MenuViewModel(
  private val executor: InteractorExecutor,
  private val getLineDetails: GetLineDetails,
  private val menuFactory: MenuFactory
) : ViewModel() {

  private var lineId = 0

  private val _menuModel = MutableLiveData<Resource<MenuModel>>()

  val menuModel: LiveData<Resource<MenuModel>>
    get() = _menuModel

  fun setArgs(lineId: Int) {
    this.lineId = lineId
  }

  fun loadLineDetails() {
    _menuModel.value = Resource.loading()
    executor(
      interactor = getLineDetails,
      request = lineId,
      onSuccess = {
        _menuModel.value = Resource.success(menuFactory.createMenu(it))
      },
      onError = {
        _menuModel.value = Resource.error(it)
      }
    )
  }
}
