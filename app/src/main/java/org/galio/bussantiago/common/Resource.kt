package org.galio.bussantiago.common

import androidx.compose.runtime.Composable
import org.galio.bussantiago.common.Status.ERROR
import org.galio.bussantiago.common.Status.LOADING
import org.galio.bussantiago.common.Status.SUCCESS

data class Resource<out T>(
  private val status: Status,
  private val data: T?,
  private val exception: Exception?
) {
  companion object {
    fun <T> success(data: T): Resource<T> {
      return Resource(SUCCESS, data, null)
    }

    fun <T> error(exception: Exception): Resource<T> {
      return Resource(ERROR, null, exception)
    }

    fun <T> loading(): Resource<T> {
      return Resource(LOADING, null, null)
    }
  }

  fun fold(
    onLoading: () -> Unit = {},
    onError: (Exception) -> Unit = {},
    onSuccess: (T) -> Unit = {}
  ) {
    when (this.status) {
      LOADING -> onLoading()
      ERROR -> onError(this.exception!!)
      SUCCESS -> onSuccess(this.data!!)
    }
  }

  @Composable
  fun Fold(
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (Exception) -> Unit = {},
    onSuccess: @Composable (T) -> Unit = {}
  ) {
    when (this.status) {
      LOADING -> onLoading()
      ERROR -> onError(this.exception!!)
      SUCCESS -> onSuccess(this.data!!)
    }
  }
}
