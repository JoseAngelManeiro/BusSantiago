package org.galio.bussantiago.features.lines

import org.galio.bussantiago.common.model.SynopticModel

data class LineModel(
  val id: Int,
  val synopticModel: SynopticModel,
  val name: String
)
