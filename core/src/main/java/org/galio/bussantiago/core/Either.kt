package org.galio.bussantiago.core

sealed class Either<out L, out R> {
  data class Error<out L>(val a: L) : Either<L, Nothing>()
  data class Success<out R>(val b: R) : Either<Nothing, R>()

  companion object {
    fun <L> error(value: L): Either<L, Nothing> = Error(value)
    fun <R> success(value: R): Either<Nothing, R> = Success(value)
  }

  val isError get() = this is Error
  val isSuccess get() = this is Success

  val errorValue: L
    get() = when (this) {
      is Error -> this.a
      is Success -> throw NoSuchElementException()
    }

  val successValue: R
    get() = when (this) {
      is Error -> throw NoSuchElementException()
      is Success -> this.b
    }

  fun <T> fold(onError: (L) -> T, onSuccess: (R) -> T): T = when (this) {
    is Error -> onError(this.errorValue)
    is Success -> onSuccess(this.successValue)
  }
}
