package org.galio.bussantiago.exception

class ServiceException(
  message: String = "Error del servidor"
) : Exception(message)
