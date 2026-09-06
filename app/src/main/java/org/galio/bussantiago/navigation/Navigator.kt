package org.galio.bussantiago.navigation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.common.model.BusStopUiModel
import org.galio.bussantiago.features.favorites.FavoritesDialogFragment
import org.galio.bussantiago.features.incidences.IncidencesFragmentArgs
import org.galio.bussantiago.features.information.InformationFragmentArgs
import org.galio.bussantiago.features.mapmarker.MapMarkerDialogFragment
import org.galio.bussantiago.features.mapmarker.MapMarkerDialogFragmentArgs
import org.galio.bussantiago.features.menu.MenuFragmentArgs
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.stops.BusStopsContainerFragmentArgs
import org.galio.bussantiago.features.times.TimesDialogFragmentArgs
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
  data object Exit : NavScreen()
  data class MapMarker(val busStop: BusStopUiModel) : NavScreen()
}

class Navigator(
  private val fragment: Fragment,
  @VisibleForTesting
  internal val navControllerProvider: () -> NavController? = getNavController(fragment),
  @VisibleForTesting
  internal val favoritesDialogFactory: () -> FavoritesDialogFragment =
    {
      FavoritesDialogFragment()
    },
  @VisibleForTesting
  internal val mapMarkerDialogFactory: (BusStopUiModel) -> MapMarkerDialogFragment =
    { busStop ->
      MapMarkerDialogFragment().apply {
        arguments = MapMarkerDialogFragmentArgs(busStopModel = busStop).toBundle()
      }
    }
) {

  fun navigate(navScreen: NavScreen) {
    when (navScreen) {
      is NavScreen.Times -> navigateSafe(
        resId = R.id.actionShowTimes,
        args = TimesDialogFragmentArgs(
          busStopCode = navScreen.busStopModel.code,
          busStopName = navScreen.busStopModel.name
        ).toBundle()
      )

      is NavScreen.MapMarker -> showBottomSheetDialog(
        dialog = mapMarkerDialogFactory(navScreen.busStop),
        tag = "MapMarkerBottomSheetFragment"
      )

      is NavScreen.BusStops -> navigateSafe(
        resId = R.id.actionShowBusStops,
        args = BusStopsContainerFragmentArgs(
          BusStopsArgs(navScreen.lineId, navScreen.routeName)
        ).toBundle()
      )

      is NavScreen.Information -> navigateSafe(
        resId = R.id.actionShowInformation,
        args = InformationFragmentArgs(navScreen.lineId).toBundle()
      )

      is NavScreen.Incidences -> navigateSafe(
        resId = R.id.actionShowIncidences,
        args = IncidencesFragmentArgs(navScreen.lineId).toBundle()
      )

      is NavScreen.LineMenu -> navigateSafe(
        resId = R.id.actionShowMenu,
        args = MenuFragmentArgs(navScreen.lineId).toBundle()
      )

      is NavScreen.Lines -> navigateSafe(
        resId = R.id.actionShowLines
      )

      is NavScreen.About -> navigateSafe(
        resId = R.id.actionShowAbout
      )

      is NavScreen.Favorites -> showBottomSheetDialog(
        dialog = favoritesDialogFactory(),
        tag = "FavoritesDialogFragment"
      )

      is NavScreen.Exit -> navControllerProvider()?.popBackStack()
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

  // We need to treat BottomSheetDialogFragment as a special case since
  // Jetpack Navigation does not officially support it as a <dialog> destination
  // in the nav_graph.xml — showing via childFragmentManager keeps interactions working.
  private fun showBottomSheetDialog(dialog: DialogFragment, tag: String) {
    // Guard against double-show (e.g., fast double-tap) which would cause
    // an IllegalStateException since the fragment is already added.
    if (fragment.childFragmentManager.findFragmentByTag(tag) != null) return
    dialog.show(fragment.childFragmentManager, tag)
  }
}

// We need to expose NavControllerProvider for testing purposes
// in order to get a NavController's mock instance
internal fun getNavController(fragment: Fragment): () -> NavController? = {
  try {
    fragment.view?.let { fragment.findNavController() }
  } catch (_: IllegalStateException) {
    null
  }
}
