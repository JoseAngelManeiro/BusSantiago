package org.galio.bussantiago.core

import org.galio.bussantiago.Either

interface Interactor<Request, Response> {

  operator fun invoke(request: Request): Either<Exception, Response>
}
