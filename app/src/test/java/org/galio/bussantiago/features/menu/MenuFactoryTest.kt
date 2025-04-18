package org.galio.bussantiago.features.menu

import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.core.model.Route
import org.galio.bussantiago.features.menu.MenuType.INCIDENCES
import org.galio.bussantiago.features.menu.MenuType.INFORMATION
import org.galio.bussantiago.features.menu.MenuType.OUTWARD_ROUTE
import org.galio.bussantiago.features.menu.MenuType.RETURN_ROUTE
import org.galio.bussantiago.features.menu.MenuType.ROUNDTRIP_ROUTE
import org.galio.bussantiago.shared.SynopticModel
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test

class MenuFactoryTest {

  private val menuFactory = MenuFactory()

  @Test
  fun `when there is only one route should return a Roundtrip route`() {
    val route = Route(name = "Roundtrip", "", emptyList())

    val result = menuFactory.createMenu(createLineDetails(listOf(route)))

    val expected = MenuModel(
      synopticModel = SynopticModel(synoptic = "Any synoptic", style = "Any style"),
      options = listOf(
        MenuOptionModel(menuType = ROUNDTRIP_ROUTE, title = "Roundtrip"),
        MenuOptionModel(menuType = INFORMATION)
      )
    )
    assertEquals(expected, result)
  }

  @Test
  fun `when there is more than one route should return an Outward and a Return route`() {
    val outwardRoute = Route(name = "Outward", "", emptyList())
    val returnRoute = Route(name = "Return", "", emptyList())

    val result = menuFactory.createMenu(createLineDetails(listOf(outwardRoute, returnRoute)))

    val expected = MenuModel(
      synopticModel = SynopticModel(synoptic = "Any synoptic", style = "Any style"),
      options = listOf(
        MenuOptionModel(menuType = OUTWARD_ROUTE, title = "Outward"),
        MenuOptionModel(menuType = RETURN_ROUTE, title = "Return"),
        MenuOptionModel(menuType = INFORMATION)
      )
    )
    assertEquals(expected, result)
  }

  @Test
  fun `when there are incidences should add the option`() {
    val route = Route(name = "Roundtrip", "", emptyList())

    val result = menuFactory.createMenu(
      createLineDetails(listOf(route), incidences = listOf(mock()))
    )

    val expected = MenuModel(
      synopticModel = SynopticModel(synoptic = "Any synoptic", style = "Any style"),
      options = listOf(
        MenuOptionModel(menuType = ROUNDTRIP_ROUTE, title = "Roundtrip"),
        MenuOptionModel(menuType = INFORMATION),
        MenuOptionModel(menuType = INCIDENCES)
      )
    )
    assertEquals(expected, result)
  }

  private fun createLineDetails(
    routes: List<Route> = emptyList(),
    incidences: List<Incidence> = emptyList()
  ): LineDetails {
    return LineDetails(
      id = 1,
      code = "Any code",
      synoptic = "Any synoptic",
      name = "Any name",
      information = "Any information",
      style = "Any style",
      routes = routes,
      incidences = incidences
    )
  }
}
