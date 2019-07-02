package org.galio.bussantiago.common

import org.galio.bussantiago.common.Status.SUCCESS
import org.galio.bussantiago.common.Status.ERROR
import org.galio.bussantiago.common.Status.LOADING

data class Resource<out T>(
  val status: Status,
  val data: T?,
  val exception: Exception?
) {
  companion object {
    fun <T> success(data: T?): Resource<T> {
      return Resource(SUCCESS, data, null)
    }

    fun <T> error(exception: Exception): Resource<T> {
      return Resource(ERROR, null, exception)
    }

    fun <T> loading(): Resource<T> {
      return Resource(LOADING, null, null)
    }
  }
}
