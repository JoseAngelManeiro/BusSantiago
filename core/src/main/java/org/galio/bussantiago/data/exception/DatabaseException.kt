package org.galio.bussantiago.data.exception

internal class DatabaseException(
  message: String = "Error en la base de datos"
) : Exception(message)
