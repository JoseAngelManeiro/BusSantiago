package org.galio.bussantiago

sealed class Either<out L, out R> {
  data class Left<out L>(val a: L) : Either<L, Nothing>()
  data class Right<out R>(val b: R) : Either<Nothing, R>()

  val isLeft get() = this is Left
  val isRight get() = this is Right

  val leftValue: L get() = when (this) {
    is Left -> this.a
    is Right -> throw NoSuchElementException()
  }

  val rightValue: R get() = when (this) {
    is Left -> throw NoSuchElementException()
    is Right -> this.b
  }
}
