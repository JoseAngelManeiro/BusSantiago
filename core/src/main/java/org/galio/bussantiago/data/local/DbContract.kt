package org.galio.bussantiago.data.local

internal object Tables {
  const val FAVOURITE = "favourite"

  // deprecated tables
  const val STOP = "stop"
  const val LINE = "line"
  const val JOURNEY = "journey"
  const val TIME = "time"
}

object FavouriteColumns {
  const val CODE = "code"
  const val NAME = "name"
}
