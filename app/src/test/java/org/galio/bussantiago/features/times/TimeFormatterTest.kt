package org.galio.bussantiago.features.times

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeFormatterTest {

    @Test
    fun `if there are 0 mins until next arrival, the literal "llegando" is returned`() {
        assertEquals("llegando", getDescriptionByMinutes(0))
    }

    @Test
    fun `if there are -1 mins until next arrival, the literal "1 min" is returned`() {
        assertEquals("<1 min", getDescriptionByMinutes(-1))
    }

    @Test
    fun `if there are -2 mins until next arrival, the literal "en parada" is returned`() {
        assertEquals("en parada", getDescriptionByMinutes(-2))
    }

    @Test
    fun `if there are less than 59 minutes until next arrival, formatted time is returned`() {
        assertEquals("48 min", getDescriptionByMinutes(48))
    }

    @Test
    fun `if there are more than 59 minutes until next arrival, formatted time is returned`() {
        assertEquals("1 h 15 min", getDescriptionByMinutes(75))
    }
}
