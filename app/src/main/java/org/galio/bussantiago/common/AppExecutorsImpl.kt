package org.galio.bussantiago.common

import android.os.Handler
import android.os.Looper
import org.galio.bussantiago.common.executor.AppExecutors
import org.galio.bussantiago.common.executor.Runner
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutorsImpl : AppExecutors {

  override val background: Runner by lazy { BackgroundRunner() }

  override val main: Runner by lazy { MainRunner() }

  private class BackgroundRunner(
    private val executorService: Executor = Executors.newSingleThreadExecutor()
  ) : Runner {
    override fun invoke(c: () -> Unit) {
      executorService.execute(c)
    }
  }

  private class MainRunner(
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
  ) : Runner {
    override fun invoke(c: () -> Unit) {
      mainThreadHandler.post(c)
    }
  }
}
