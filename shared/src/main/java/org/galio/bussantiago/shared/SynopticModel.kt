package org.galio.bussantiago.shared

data class SynopticModel(
  val synoptic: String,
  val style: String
) {

  fun getSynopticFormatted(): String = synoptic.removePrefix("L")
}
