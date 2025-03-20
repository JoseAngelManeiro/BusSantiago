package org.galio.bussantiago.widget

data class SynopticModel(
  val synoptic: String,
  val style: String
) {

  fun getSynopticFormatted(): String = synoptic.removePrefix("L")
}
