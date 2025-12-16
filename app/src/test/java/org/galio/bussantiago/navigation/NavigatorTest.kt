package org.galio.bussantiago.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.features.favorites.FavoritesDialogFragment
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.util.argumentCaptor
import org.galio.bussantiago.util.capture
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.firstValue
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NavigatorTest {

  @Test
  fun `navigate to Favorites should show FavoritesDialogFragment`() {
    val fragmentManager = mock<FragmentManager>()
    val fragment = mock<Fragment> { on { childFragmentManager } doReturn fragmentManager }
    val favoritesDialog = mock<FavoritesDialogFragment>()
    val favoritesDialogFactory: () -> FavoritesDialogFragment = { favoritesDialog }
    val navigator = Navigator(fragment, favoritesDialogFactory = favoritesDialogFactory)

    navigator.navigate(NavScreen.Favorites)

    verify(favoritesDialog).show(fragmentManager, "FavoritesDialogFragment")
  }

  @Test
  fun `navigate to Exit should go back`() {
    val navController = mock<NavController>()
    val fragment = mock<Fragment> { on { view } doReturn mock<View>() }
    val navControllerProvider: () -> NavController? = { navController }
    val navigator = Navigator(fragment, navControllerProvider)

    navigator.navigate(NavScreen.Exit)

    verify(navController).popBackStack()
  }

  @Test
  fun `navigateSafe destinations should trigger navigation with expected args`() {
    val navController = mock<NavController>()
    val fragment = mock<Fragment> { on { view } doReturn mock<View>() }
    val navControllerProvider: () -> NavController? = { navController }
    val navigator = Navigator(fragment, navControllerProvider)

    for (case in getNavigationCases()) {
      val mockDestination = mock<NavDestination> {
        on { getAction(case.navActionResId) } doReturn mock<NavAction>()
      }
      whenever(navController.currentDestination).thenReturn(mockDestination)
      whenever(fragment.isAdded).thenReturn(true)

      navigator.navigate(case.screen)

      val argCaptor = argumentCaptor<Bundle?>()
      verify(navController).navigate(eq(case.navActionResId), capture(argCaptor))
      // argCaptor (Bundle) can be null when the Fragment to open not need arguments
      assertTrue(
        "${case.screen} arguments don't match expected",
        argCaptor.firstValue == null || case.argsChecker(argCaptor.firstValue!!)
      )
    }
  }

  private fun getNavigationCases(): List<NavigationCase> {
    return listOf(
      NavigationCase(
        screen = NavScreen.Times(BusStopModel("123", "Main St")),
        navActionResId = R.id.actionShowTimes,
        argsChecker = { bundle ->
          @Suppress("DEPRECATION") // Roboelectric still uses legacy sdk versions
          val extracted = bundle.getParcelable("bus_stop_key") as? BusStopModel
          extracted?.code == "123" && extracted.name == "Main St"
        }
      ),
      NavigationCase(
        screen = NavScreen.BusStops(42, "Blue Line"),
        navActionResId = R.id.actionShowBusStops,
        argsChecker = { bundle ->
          @Suppress("DEPRECATION") // Roboelectric still uses legacy sdk versions
          val args = bundle.getParcelable("bus_stops_args_key") as? BusStopsArgs
          args?.lineId == 42 && args.routeName == "Blue Line"
        }
      ),
      NavigationCase(
        screen = NavScreen.Information(77),
        navActionResId = R.id.actionShowInformation,
        argsChecker = { bundle ->
          bundle.getInt("id_key") == 77
        }
      ),
      NavigationCase(
        screen = NavScreen.Incidences(76),
        navActionResId = R.id.actionShowIncidences,
        argsChecker = { bundle ->
          bundle.getInt("lineId") == 76
        }
      ),
      NavigationCase(
        screen = NavScreen.LineMenu(75),
        navActionResId = R.id.actionShowMenu,
        argsChecker = { bundle ->
          bundle.getInt("id_key") == 75
        }
      ),
      NavigationCase(
        screen = NavScreen.Lines,
        navActionResId = R.id.actionShowLines,
        argsChecker = { any() } // There is not arguments to check
      ),
      NavigationCase(
        screen = NavScreen.About,
        navActionResId = R.id.actionShowAbout,
        argsChecker = { any() }
      )
    )
  }

  private class NavigationCase(
    val screen: NavScreen,
    val navActionResId: Int,
    val argsChecker: (Bundle) -> Boolean
  )
}

