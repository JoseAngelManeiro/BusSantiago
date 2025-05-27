package org.galio.bussantiago.util

import org.galio.bussantiago.core.Interactor
import org.galio.bussantiago.executor.InteractorExecutor

class TestInteractorExecutor : InteractorExecutor() {

  override operator fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit,
    onSuccess: (Response) -> Unit
  ) {
    val response = interactor(request)
    if (response.isSuccess) {
      onSuccess(response.getOrNull()!!)
    } else {
      onError(response.exceptionOrNull() as Exception)
    }
  }
}
