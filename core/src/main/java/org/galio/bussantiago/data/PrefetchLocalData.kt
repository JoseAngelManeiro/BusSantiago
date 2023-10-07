package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right

abstract class PrefetchLocalData<RequestType, ResultType> {

  fun load(): Either<Exception, ResultType> {
    val localData = loadFromLocal()
    return if (shouldFetch(localData)) {
      val serviceData = loadFromService()
      if (serviceData.isRight) {
        saveServiceResult(serviceData.rightValue)
        Right(loadFromLocal()!!)
      } else {
        Left(serviceData.leftValue)
      }
    } else {
      Right(localData!!)
    }
  }

  abstract fun loadFromLocal(): ResultType?

  abstract fun shouldFetch(data: ResultType?): Boolean

  abstract fun loadFromService(): Either<Exception, RequestType>

  abstract fun saveServiceResult(item: RequestType)
}
