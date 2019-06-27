package org.galio.bussantiago.common

abstract class Interactor<Request, Response>(
  private val appExecutors: AppExecutors
) {

  abstract fun execute(request: Request): Either<Exception, Response>

  operator fun invoke(
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  ) {
    appExecutors.bg.execute {
      val response = execute(request)
      appExecutors.ui.execute {
        if (response.isRight) {
          onSuccess(response.rightValue)
        } else {
          onError(response.leftValue)
        }
      }
    }
  }
}
