package org.galio.bussantiago.features.menu

import org.galio.bussantiago.common.model.SynopticModel
import org.galio.bussantiago.domain.model.Incidence
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.model.Route
import org.galio.bussantiago.features.menu.MenuType.INCIDENCES
import org.galio.bussantiago.features.menu.MenuType.OUTWARD_ROUTE
import org.galio.bussantiago.features.menu.MenuType.RETURN_ROUTE
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Calendar

class MenuFactoryTest {

  companion object {
    private const val OUTWARD_NAME = "Outward route"
    private const val RETURN_NAME = "Return route"

    private val outwardRoute = Route(OUTWARD_NAME, "Any direction", mock())
    private val returnRoute = Route(RETURN_NAME, "Any direction", mock())
  }

  private val menuFactory = MenuFactory()

  @Test
  fun `if there is only one route, this will be a Outward option`() {
    val routes = listOf(outwardRoute)

    val result = menuFactory.createMenu(createLineDetails(routes)).options

    assertNotNull(result.findByMenuType(OUTWARD_ROUTE))
    assertEquals(OUTWARD_NAME, result.findByMenuType(OUTWARD_ROUTE)!!.title)
    assertNull(result.findByMenuType(RETURN_ROUTE))
  }

  @Test
  fun `if there are two routes, there will be a Outward option and a Return option`() {
    val routes = listOf(outwardRoute, returnRoute)

    val result = menuFactory.createMenu(createLineDetails(routes)).options

    assertNotNull(result.findByMenuType(OUTWARD_ROUTE))
    assertEquals(OUTWARD_NAME, result.findByMenuType(OUTWARD_ROUTE)!!.title)
    assertNotNull(result.findByMenuType(RETURN_ROUTE))
    assertEquals(RETURN_NAME, result.findByMenuType(RETURN_ROUTE)!!.title)
  }

  @Test
  fun `if there are no incidences, the option does not appear`() {
    val result = menuFactory.createMenu(createLineDetails()).options

    assertNull(result.findByMenuType(INCIDENCES))
  }

  @Test
  fun `if there are incidences, the option appears`() {
    val incidenceStub = Incidence(
      id = 1,
      title = "Any incidence",
      description = "Any desc",
      startDate = Calendar.getInstance().time,
      endDate = null
    )

    val result = menuFactory
      .createMenu(createLineDetails(incidences = listOf(incidenceStub)))
      .options

    assertNotNull(result.findByMenuType(INCIDENCES))
  }

  @Test
  fun `when the menu model is created should include the synoptic model`() {
    val result = menuFactory.createMenu(createLineDetails())

    assertEquals(SynopticModel("Any synoptic", "Any style"), result.synopticModel)
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

  private fun List<MenuOptionModel>.findByMenuType(menuType: MenuType): MenuOptionModel? {
    return this.find { it.menuType == menuType }
  }
}
