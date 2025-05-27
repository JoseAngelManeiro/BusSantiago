package org.galio.bussantiago.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.galio.bussantiago.core.Interactor

class AsyncInteractorExecutor : InteractorExecutor() {

  override fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit,
    onSuccess: (Response) -> Unit
  ) {
    getViewModelScope()?.launch(Dispatchers.IO) {
      val response = interactor(request)
      withContext(Dispatchers.Main) {
        response.fold(
          onSuccess = { onSuccess(it) },
          onFailure = { onError(it as Exception) }
        )
      }
    } ?: throw IllegalStateException("InteractorExecutor's scope is not initialized")
  }
}
