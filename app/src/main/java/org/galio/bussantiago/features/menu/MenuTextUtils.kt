package org.galio.bussantiago.features.menu

import android.content.Context
import org.galio.bussantiago.R

class MenuTextUtils {

  fun getIconResource(menuType: MenuType): Int {
    return when (menuType) {
      MenuType.OUTWARD_ROUTE -> R.drawable.ic_outward_route
      MenuType.RETURN_ROUTE -> R.drawable.ic_return_route
      MenuType.ROUNDTRIP_ROUTE -> R.drawable.ic_roundtrip_route
      MenuType.INFORMATION -> R.drawable.ic_information
      MenuType.INCIDENCES -> R.drawable.ic_incidences
    }
  }

  fun getContentDescription(context: Context, menuType: MenuType): String {
    return when (menuType) {
      MenuType.OUTWARD_ROUTE -> context.getString(R.string.outward_route_stops)
      MenuType.RETURN_ROUTE -> context.getString(R.string.return_route_stops)
      MenuType.ROUNDTRIP_ROUTE -> context.getString(R.string.roundtrip_route_stops)
      MenuType.INFORMATION -> context.getString(R.string.information)
      MenuType.INCIDENCES -> context.getString(R.string.incidences)
    }
  }

  fun getTitle(context: Context, menuOptionModel: MenuOptionModel): String {
    return when (menuOptionModel.menuType) {
      MenuType.INFORMATION -> context.getString(R.string.information)
      MenuType.INCIDENCES -> context.getString(R.string.incidences)
      else -> menuOptionModel.title.orEmpty()
    }
  }
}
