package org.galio.bussantiago.data.cache

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LineDetailsCacheTest {

  private lateinit var lineDetailsCache: LineDetailsCache

  @Before
  fun setUp() {
    lineDetailsCache = LineDetailsCache()
  }

  @Test
  fun `when the key exists should return the value expected`() {
    val lineDetails1 = mock<LineDetails>()
    val lineDetails2 = mock<LineDetails>()

    lineDetailsCache.save(1, lineDetails1)
    lineDetailsCache.save(2, lineDetails2)

    assertEquals(lineDetails1, lineDetailsCache.get(1))
    assertEquals(lineDetails2, lineDetailsCache.get(2))
  }

  @Test
  fun `when the key does not exist should return null`() {
    val lineDetails1 = mock<LineDetails>()

    lineDetailsCache.save(1, lineDetails1)

    assertEquals(lineDetails1, lineDetailsCache.get(1))
    assertEquals(null, lineDetailsCache.get(2))
  }
}
