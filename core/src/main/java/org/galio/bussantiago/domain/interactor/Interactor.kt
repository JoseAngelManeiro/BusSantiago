package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either

interface Interactor<Request, Response> {

  operator fun invoke(request: Request): Either<Exception, Response>
}
