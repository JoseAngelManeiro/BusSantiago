package org.galio.bussantiago.domain

import org.galio.bussantiago.common.Either

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
