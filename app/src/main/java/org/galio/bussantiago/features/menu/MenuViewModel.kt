package org.galio.bussantiago.features.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.navigation.NavScreen

class MenuViewModel(
  private val executor: InteractorExecutor,
  private val getLineDetails: GetLineDetails,
  private val menuFactory: MenuFactory
) : BaseViewModel(executor) {

  private val _menuModel = MutableLiveData<Resource<MenuModel>>()
  private val _navigationEvent = SingleLiveEvent<NavScreen>()

  val menuModel: LiveData<Resource<MenuModel>>
    get() = _menuModel

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

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
    val navScreen = when (menuOptionModel.menuType) {
      MenuType.OUTWARD_ROUTE, MenuType.RETURN_ROUTE, MenuType.ROUNDTRIP_ROUTE -> {
        NavScreen.BusStops(lineId, menuOptionModel.title.orEmpty())
      }

      MenuType.INFORMATION -> {
        NavScreen.Information(lineId)
      }

      MenuType.INCIDENCES -> {
        NavScreen.Incidences(lineId)
      }
    }
    _navigationEvent.value = navScreen
  }
}
