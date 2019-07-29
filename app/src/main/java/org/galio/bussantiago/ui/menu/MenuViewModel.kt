package org.galio.bussantiago.ui.menu

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

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
