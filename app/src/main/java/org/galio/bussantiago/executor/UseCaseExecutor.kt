package org.galio.bussantiago.executor

import kotlinx.coroutines.CoroutineScope

abstract class UseCaseExecutor {

  private var scope: CoroutineScope? = null

  fun setViewModelScope(viewModelScope: CoroutineScope) {
    scope = viewModelScope
  }

  fun getViewModelScope(): CoroutineScope? {
    return scope
  }

  abstract operator fun <Response> invoke(
    useCase: () -> Result<Response>,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  )
}
