package org.galio.bussantiago.core

interface Interactor<Request, Response> {

  operator fun invoke(request: Request): Either<Exception, Response>
}
