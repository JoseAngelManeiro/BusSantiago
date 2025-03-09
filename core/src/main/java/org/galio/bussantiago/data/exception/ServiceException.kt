package org.galio.bussantiago.data.exception

internal class ServiceException(
  message: String = "Error del servidor"
) : Exception(message)
