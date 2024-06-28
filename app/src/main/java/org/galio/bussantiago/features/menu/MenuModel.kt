package org.galio.bussantiago.features.menu

import org.galio.bussantiago.common.model.SynopticModel

data class MenuModel(
  val synopticModel: SynopticModel,
  val options: List<MenuOptionModel>
)
