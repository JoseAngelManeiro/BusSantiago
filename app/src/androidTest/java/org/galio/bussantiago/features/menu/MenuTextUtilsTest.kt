package org.galio.bussantiago.features.menu

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.galio.bussantiago.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuTextUtilsTest {

  private lateinit var context: Context
  private lateinit var utils: MenuTextUtils

  @Before
  fun setUp() {
    context = ApplicationProvider.getApplicationContext()
    utils = MenuTextUtils()
  }

  @Test
  fun test_getIconResource() {
    assertEquals(R.drawable.ic_outward_route, utils.getIconResource(MenuType.OUTWARD_ROUTE))
    assertEquals(R.drawable.ic_return_route, utils.getIconResource(MenuType.RETURN_ROUTE))
    assertEquals(R.drawable.ic_roundtrip_route, utils.getIconResource(MenuType.ROUNDTRIP_ROUTE))
    assertEquals(R.drawable.ic_information, utils.getIconResource(MenuType.INFORMATION))
    assertEquals(R.drawable.ic_incidences, utils.getIconResource(MenuType.INCIDENCES))
  }

  @Test
  fun test_getContentDescription() {
    assertEquals("Paradas ida", utils.getContentDescription(context, MenuType.OUTWARD_ROUTE))
    assertEquals("Paradas vuelta", utils.getContentDescription(context, MenuType.RETURN_ROUTE))
    assertEquals("Paradas", utils.getContentDescription(context, MenuType.ROUNDTRIP_ROUTE))
    assertEquals("Información", utils.getContentDescription(context, MenuType.INFORMATION))
    assertEquals("Incidencias", utils.getContentDescription(context, MenuType.INCIDENCES))
  }

  @Test
  fun test_getTitle_outward_route() {
    val model = MenuOptionModel(MenuType.OUTWARD_ROUTE, "Outward")

    assertEquals("Outward", utils.getTitle(context, model))
  }

  @Test
  fun test_getTitle_return_route() {
    val model = MenuOptionModel(MenuType.RETURN_ROUTE, "Return")

    assertEquals("Return", utils.getTitle(context, model))
  }

  @Test
  fun test_getTitle_roundtrip_route() {
    val model = MenuOptionModel(MenuType.ROUNDTRIP_ROUTE, "Roundtrip")

    assertEquals("Roundtrip", utils.getTitle(context, model))
  }

  @Test
  fun test_getTitle_information() {
    val model = MenuOptionModel(MenuType.INFORMATION, null)

    assertEquals("Información", utils.getTitle(context, model))
  }

  @Test
  fun test_getTitle_incidences() {
    val model = MenuOptionModel(MenuType.INCIDENCES, null)

    assertEquals("Incidencias", utils.getTitle(context, model))
  }

  @Test
  fun test_getTitle_fallbackToEmpty() {
    val model = MenuOptionModel(MenuType.OUTWARD_ROUTE, null)

    assertEquals("", utils.getTitle(context, model))
  }
}
