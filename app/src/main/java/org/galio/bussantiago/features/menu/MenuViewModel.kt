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

  private val _menuModel = MutableLiveData<Resource<MenuModel>>()

  val menuModel: LiveData<Resource<MenuModel>>
    get() = _menuModel

  fun loadLineDetails(id: Int) {
    _menuModel.value = Resource.loading()
    executor(
      interactor = getLineDetails,
      request = id,
      onSuccess = {
        _menuModel.value = Resource.success(menuFactory.createMenu(it))
      },
      onError = {
        _menuModel.value = Resource.error(it)
      }
    )
  }
}
