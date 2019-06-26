package org.galio.bussantiago.common

import android.os.Looper
import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class Interactor<Request, Response> {

  private val bgExecutor = Executors.newSingleThreadExecutor()
  private val uiExecutor: Executor = MainThreadExecutor()

  abstract fun execute(request: Request): Either<Exception, Response>

  operator fun invoke(
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  ) {
    try {
      bgExecutor.execute {
        val response = execute(request)
        uiExecutor.execute {
          if (response.isRight) {
            onSuccess(response.rightValue)
          } else {
            onError(response.leftValue)
          }
        }
      }
    } finally {
      bgExecutor.shutdown()
    }
  }

  private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
      mainThreadHandler.post(command)
    }
  }
}
