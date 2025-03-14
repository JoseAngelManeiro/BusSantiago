package org.galio.bussantiago.data.cache

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LineCacheTest {

  private lateinit var lineCache: LineCache

  @Before
  fun setUp() {
    lineCache = LineCache()
  }

  @Test
  fun `when cache has no data should return an empty list`() {
    assertEquals(emptyList<Line>(), lineCache.getAll())
  }

  @Test
  fun `when cache has data should return the data saved`() {
    val lines = listOf<Line>(mock(), mock())

    lineCache.save(lines)

    assertEquals(lines, lineCache.getAll())
  }

  @Test
  fun `when cache saves new data should remove all previous information`() {
    val oldLines = listOf<Line>(mock(), mock())
    val newLines = listOf<Line>(mock(), mock())

    lineCache.save(oldLines)
    assertEquals(oldLines, lineCache.getAll())

    lineCache.save(newLines)
    assertEquals(newLines, lineCache.getAll())
  }
}
