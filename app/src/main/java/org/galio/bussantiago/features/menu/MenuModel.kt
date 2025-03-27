package org.galio.bussantiago.features.menu

import org.galio.bussantiago.shared.SynopticModel

data class MenuModel(
  val synopticModel: SynopticModel,
  val options: List<MenuOptionModel>
)
