package org.galio.bussantiago.executor

import kotlinx.coroutines.CoroutineScope
import org.galio.bussantiago.core.Interactor

abstract class InteractorExecutor {

  private var scope: CoroutineScope? = null

  fun setViewModelScope(viewModelScope: CoroutineScope) {
    scope = viewModelScope
  }

  fun getViewModelScope(): CoroutineScope? {
    return scope
  }

  abstract operator fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  )
}
