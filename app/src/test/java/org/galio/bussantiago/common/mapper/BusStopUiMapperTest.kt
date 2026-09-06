package org.galio.bussantiago.common.mapper

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineSearch
import org.junit.Assert.assertEquals
import org.junit.Test

class BusStopUiMapperTest {

    private val mapper = BusStopUiMapper()

    @Test
    fun `map should correctly transform BusStopSearch to BusStopUiModel`() {
        // Given
        val coreLines = listOf(
            LineSearch(synoptic = "L1", style = "#FF0000"),
            LineSearch(synoptic = "L5", style = "#00FF00")
        )
        val coreModel = BusStopSearch(
            id = 123,
            code = "123",
            name = "Plaza Galicia",
            zone = "Centro",
            coordinates = Coordinates(42.87, -8.54),
            lines = coreLines
        )

        // When
        val result = mapper.map(coreModel)

        // Then
        assertEquals("123", result.code)
        assertEquals("Plaza Galicia", result.name)
        assertEquals(2, result.lines.size)
        
        assertEquals("L1", result.lines[0].synoptic)
        assertEquals("#FF0000", result.lines[0].style)
        
        assertEquals("L5", result.lines[1].synoptic)
        assertEquals("#00FF00", result.lines[1].style)
    }
}
