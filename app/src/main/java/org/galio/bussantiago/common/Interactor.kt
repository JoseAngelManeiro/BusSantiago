package org.galio.bussantiago.common

import org.galio.bussantiago.common.executor.AppExecutors

abstract class Interactor<Request, Response>(
  private val appExecutors: AppExecutors
) {

  abstract fun execute(request: Request): Either<Exception, Response>

  operator fun invoke(
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  ) {
    appExecutors.background {
      val response = execute(request)
      appExecutors.main {
        if (response.isRight) {
          onSuccess(response.rightValue)
        } else {
          onError(response.leftValue)
        }
      }
    }
  }
}
