package org.galio.bussantiago.ui.information

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Either.Right
import org.galio.bussantiago.common.Either.Left
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.repository.LineDetailsRepository

class GetLineInformation(
  private val lineDetailsRepository: LineDetailsRepository
) : Interactor<Int, String> {

  override fun invoke(request: Int): Either<Exception, String> {
    val response = lineDetailsRepository.getLineDetails(request)
    return if (response.isRight) {
      Right(response.rightValue.information)
    } else {
      Left(response.leftValue)
    }
  }
}
