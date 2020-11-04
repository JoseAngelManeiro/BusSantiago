package org.galio.bussantiago.executor

import org.galio.bussantiago.domain.interactor.Interactor

class SyncInteractorExecutor : InteractorExecutor {

  override fun <Request, Response> invoke(
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
