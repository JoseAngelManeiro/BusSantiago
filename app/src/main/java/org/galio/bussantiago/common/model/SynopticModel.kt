package org.galio.bussantiago.common.model

data class SynopticModel(
  val synoptic: String,
  val style: String
) {

  fun getSynopticFormatted(): String = synoptic.removePrefix("L")
}
