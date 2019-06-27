package org.galio.bussantiago.common

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
  val bg: Executor = Executors.newSingleThreadExecutor(),
  val ui: Executor = MainThreadExecutor()
) {

  private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
      mainThreadHandler.post(command)
    }
  }
}
