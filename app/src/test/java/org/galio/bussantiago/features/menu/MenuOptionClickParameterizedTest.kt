package org.galio.bussantiago.features.menu

import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.util.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.verify

// Parameterized tests for MenuViewModel — see MenuViewModelTest.kt for standard tests
@RunWith(Parameterized::class)
class MenuOptionClickParameterizedTest(
  private val menuOptionModel: MenuOptionModel,
  private val lineId: Int,
  private val navScreenExpected: NavScreen
) {

  private val navigator = mock<Navigator>()
  private val viewModel = MenuViewModel(mock(), mock(), mock(), navigator)

  @Test
  fun `when menu option is clicked should navigate to screen expected`() {
    viewModel.onMenuOptionClicked(menuOptionModel, lineId)

    verify(navigator).navigate(navScreenExpected)
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(
      name = "menuOptionModel: {0}, lineId: {1}, navScreenExpected: {2}"
    )
    fun data(): Collection<Array<Any>> {
      return listOf(
        arrayOf(
          MenuOptionModel(menuType = MenuType.OUTWARD_ROUTE, title = "outward"),
          1,
          NavScreen.BusStops(1, "outward")
        ),
        arrayOf(
          MenuOptionModel(menuType = MenuType.RETURN_ROUTE, title = "return"),
          1,
          NavScreen.BusStops(1, "return")
        ),
        arrayOf(
          MenuOptionModel(menuType = MenuType.ROUNDTRIP_ROUTE, title = "roundtrip"),
          1,
          NavScreen.BusStops(1, "roundtrip")
        ),
        arrayOf(
          MenuOptionModel(menuType = MenuType.INFORMATION),
          1,
          NavScreen.Information(1)
        ),
        arrayOf(
          MenuOptionModel(menuType = MenuType.INCIDENCES),
          1,
          NavScreen.Incidences(1)
        )
      )
    }
  }
}
