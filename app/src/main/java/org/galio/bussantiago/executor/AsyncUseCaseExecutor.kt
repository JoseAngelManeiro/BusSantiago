package org.galio.bussantiago.executor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsyncUseCaseExecutor : UseCaseExecutor() {

  override fun <Response> invoke(
    useCase: () -> Result<Response>,
    onError: (Exception) -> Unit,
    onSuccess: (Response) -> Unit
  ) {
    getViewModelScope()?.launch(Dispatchers.IO) {
      val response = useCase()
      withContext(Dispatchers.Main) {
        response.fold(
          onSuccess = { onSuccess(it) },
          onFailure = { onError(it as Exception) }
        )
      }
    } ?: throw IllegalStateException("InteractorExecutor's scope is not initialized")
  }
}
