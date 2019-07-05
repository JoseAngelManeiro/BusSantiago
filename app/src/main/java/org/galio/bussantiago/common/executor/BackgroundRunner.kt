package org.galio.bussantiago.common.executor

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BackgroundRunner(
  private val executorService: Executor = Executors.newSingleThreadExecutor()
) : Runner {

  override fun invoke(c: () -> Unit) {
    executorService.execute(c)
  }
}
