package org.galio.bussantiago.common.mapper

import org.galio.bussantiago.common.model.BusStopUiModel
import org.galio.bussantiago.common.model.LineUiModel
import org.galio.bussantiago.core.model.BusStopSearch

class BusStopUiMapper {
    fun map(busStopSearch: BusStopSearch): BusStopUiModel {
        return BusStopUiModel(
            code = busStopSearch.code,
            name = busStopSearch.name,
            lines = busStopSearch.lines.map { LineUiModel(it.synoptic, it.style) }
        )
    }
}
