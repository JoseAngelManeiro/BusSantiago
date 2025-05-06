package org.galio.bussantiago.features.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator

class MenuViewModel(
  private val executor: InteractorExecutor,
  private val getLineDetails: GetLineDetails,
  private val menuFactory: MenuFactory,
  private val navigator: Navigator
) : BaseViewModel(executor) {

  private val _menuModel = MutableLiveData<Resource<MenuModel>>()

  val menuModel: LiveData<Resource<MenuModel>>
    get() = _menuModel

  fun loadLineDetails(lineId: Int) {
    _menuModel.value = Resource.loading()
    executor(
      interactor = getLineDetails,
      request = lineId,
      onSuccess = { _menuModel.value = Resource.success(menuFactory.createMenu(it)) },
      onError = { _menuModel.value = Resource.error(it) }
    )
  }

  fun onMenuOptionClicked(menuOptionModel: MenuOptionModel, lineId: Int) {
    when (menuOptionModel.menuType) {
      MenuType.OUTWARD_ROUTE, MenuType.RETURN_ROUTE, MenuType.ROUNDTRIP_ROUTE -> {
        navigator.navigate(NavScreen.BusStops(lineId, menuOptionModel.title.orEmpty()))
      }
      MenuType.INFORMATION -> {
        navigator.navigate(NavScreen.Information(lineId))
      }
      MenuType.INCIDENCES -> {
        navigator.navigate(NavScreen.Incidences(lineId))
      }
    }
  }
}
