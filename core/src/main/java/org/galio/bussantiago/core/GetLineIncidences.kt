package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.Incidence

interface GetLineIncidences {
  operator fun invoke(lineId: Int): Result<List<Incidence>>
}
