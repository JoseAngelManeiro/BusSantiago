package org.galio.bussantiago.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.features.favorites.FavoritesDialogFragment
import org.galio.bussantiago.features.incidences.IncidencesFragmentArgs
import org.galio.bussantiago.features.information.InformationFragment
import org.galio.bussantiago.features.menu.MenuFragment
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.stops.BusStopsContainerFragment
import org.galio.bussantiago.features.times.TimesDialogFragment
import org.jetbrains.annotations.VisibleForTesting

sealed class NavScreen {
  data class Times(val busStopModel: BusStopModel) : NavScreen()
  data class BusStops(val lineId: Int, val routeName: String) : NavScreen()
  data class Information(val lineId: Int) : NavScreen()
  data class Incidences(val lineId: Int) : NavScreen()
  data class LineMenu(val lineId: Int) : NavScreen()
  data object Lines : NavScreen()
  data object About : NavScreen()
  data object Favorites : NavScreen()
}

class Navigator(
  private val fragment: Fragment,
  @VisibleForTesting
  internal val navControllerProvider: () -> NavController? = getNavController(fragment),
  @VisibleForTesting
  internal val favoritesDialogFactory: () -> FavoritesDialogFragment = { FavoritesDialogFragment() }
) {

  fun navigate(navScreen: NavScreen) {
    when (navScreen) {
      is NavScreen.Times -> navigateSafe(
        resId = R.id.actionShowTimes,
        args = TimesDialogFragment.createArguments(navScreen.busStopModel)
      )

      is NavScreen.BusStops -> navigateSafe(
        resId = R.id.actionShowBusStops,
        args = BusStopsContainerFragment.createArguments(
          BusStopsArgs(navScreen.lineId, navScreen.routeName)
        )
      )

      is NavScreen.Information -> navigateSafe(
        resId = R.id.actionShowInformation,
        args = InformationFragment.createArguments(navScreen.lineId)
      )

      is NavScreen.Incidences -> navigateSafe(
        resId = R.id.actionShowIncidences,
        args = IncidencesFragmentArgs(navScreen.lineId).toBundle()
      )

      is NavScreen.LineMenu -> navigateSafe(
        resId = R.id.actionShowMenu,
        args = MenuFragment.createArguments(navScreen.lineId)
      )

      is NavScreen.Lines -> navigateSafe(
        resId = R.id.actionShowLines
      )

      is NavScreen.About -> navigateSafe(
        resId = R.id.actionShowAbout
      )

      is NavScreen.Favorites -> navigateToFavorites()
    }
  }

  private fun navigateSafe(resId: Int, args: Bundle? = null) {
    // Try getting the NavController only if the Fragment is still attached and has a valid view
    val navController = navControllerProvider()
    val action = navController?.currentDestination?.getAction(resId)
    // If the Fragment is still added to the Activity and the action is valid
    if (fragment.isAdded && action != null) {
      navController.navigate(resId, args)
    }
  }

  // We need to treat this as a special case since
  // Jetpack Navigation does not officially support BottomSheetDialogFragment
  // as a <dialog> destination in the nav_graph.xml
  private fun navigateToFavorites() {
    favoritesDialogFactory().show(fragment.childFragmentManager, "FavoritesDialogFragment")
  }
}

// We need to expose NavControllerProvider for testing purposes
// in order to get a NavController's mock instance
internal fun getNavController(fragment: Fragment): () -> NavController? = {
  try {
    fragment.view?.let { fragment.findNavController() }
  } catch (e: IllegalStateException) {
    null
  }
}
