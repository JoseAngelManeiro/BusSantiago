package org.galio.bussantiago.common.executor

import org.galio.bussantiago.domain.Interactor

interface InteractorExecutor {

  operator fun <Request, Response> invoke(
    interactor: Interactor<Request, Response>,
    request: Request,
    onError: (Exception) -> Unit = {},
    onSuccess: (Response) -> Unit = {}
  )
}
