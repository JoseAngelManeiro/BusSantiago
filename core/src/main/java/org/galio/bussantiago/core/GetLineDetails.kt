package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.LineDetails

interface GetLineDetails {
  operator fun invoke(lineId: Int): Result<LineDetails>
}
