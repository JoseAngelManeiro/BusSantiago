package org.galio.bussantiago.ui.menu

import org.galio.bussantiago.domain.model.LineDetails

class MenuFactory {

  fun createMenu(lineDetails: LineDetails): MenuModel {
    val menuOptionModels = mutableListOf<MenuOptionModel>()

    lineDetails.routes.forEachIndexed { index, route ->
      menuOptionModels.add(
        MenuOptionModel(
          menuType = if (index == 0) MenuType.OUTWARD_ROUTE else MenuType.RETURN_ROUTE,
          title = route.name
        )
      )
    }
    menuOptionModels.add(MenuOptionModel(MenuType.INFORMATION))
    if (lineDetails.incidences.isNotEmpty()) {
      menuOptionModels.add(MenuOptionModel(MenuType.INCIDENCES))
    }

    return MenuModel(lineDetails.id, menuOptionModels)
  }
}
