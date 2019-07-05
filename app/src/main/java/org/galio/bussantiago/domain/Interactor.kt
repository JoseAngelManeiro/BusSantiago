package org.galio.bussantiago.domain

import org.galio.bussantiago.common.Either

interface Interactor<Request, Response> {

  operator fun invoke(request: Request): Either<Exception, Response>
}
