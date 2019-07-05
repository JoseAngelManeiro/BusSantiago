package org.galio.bussantiago.common.executor

import org.galio.bussantiago.domain.Interactor

class AsyncInteractorExecutor(
  private val runOnMainThread: Runner,
  private val runOnBgThread: Runner
) : InteractorExecutor {

  override fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit,
    onSuccess: (Response) -> Unit
  ) {
    runOnBgThread {
      val response = interactor(request)
      runOnMainThread {
        if (response.isRight) {
          onSuccess(response.rightValue)
        } else {
          onError(response.leftValue)
        }
      }
    }
  }
}
