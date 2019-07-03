package org.galio.bussantiago.common.exception

class ServiceException(
  message: String = "An error has occurred with the server"
) : Exception(message)
