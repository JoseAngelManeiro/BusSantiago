package org.galio.bussantiago.features.lines

import org.galio.bussantiago.shared.SynopticModel

data class LineModel(
  val id: Int,
  val synopticModel: SynopticModel,
  val name: String,
  val incidents: Int
)
