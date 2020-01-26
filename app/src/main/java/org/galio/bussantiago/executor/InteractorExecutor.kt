package org.galio.bussantiago.executor

import org.galio.bussantiago.domain.interactor.Interactor

interface InteractorExecutor {

  operator fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  )
}
