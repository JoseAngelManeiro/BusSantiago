package org.galio.bussantiago.util

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.executor.InteractorExecutor

class TestInteractorExecutor : InteractorExecutor() {

  override operator fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit,
    onSuccess: (Response) -> Unit
  ) {
    val response = interactor(request)
    if (response.isRight) {
      onSuccess(response.rightValue)
    } else {
      onError(response.leftValue)
    }
  }
}
