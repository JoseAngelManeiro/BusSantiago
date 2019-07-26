package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Either.Right
import org.galio.bussantiago.common.Either.Left

abstract class PrefetchLocalData<RequestType, ResultType> {

  fun load(): Either<Exception, ResultType> {
    val localData = loadFromLocal()
    return if (localData == null) {
      val serviceData = loadFromService()
      if (serviceData.isRight) {
        saveServiceResult(serviceData.rightValue)
        Right(loadFromLocal()!!)
      } else {
        Left(serviceData.leftValue)
      }
    } else {
      Right(localData)
    }
  }

  abstract fun loadFromLocal(): ResultType?

  abstract fun loadFromService(): Either<Exception, RequestType>

  abstract fun saveServiceResult(item: RequestType)
}
