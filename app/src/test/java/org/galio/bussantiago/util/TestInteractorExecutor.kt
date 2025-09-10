package org.galio.bussantiago.util

import org.galio.bussantiago.executor.UseCaseExecutor

class TestUseCaseExecutor : UseCaseExecutor() {

  override fun <Response> invoke(
    useCase: () -> Result<Response>,
    onError: (Exception) -> Unit,
    onSuccess: (Response) -> Unit
  ) {
    val response = useCase()
    if (response.isSuccess) {
      onSuccess(response.getOrNull()!!)
    } else {
      onError(response.exceptionOrNull() as Exception)
    }
  }
}
