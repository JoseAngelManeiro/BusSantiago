package org.galio.bussantiago.util

import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

inline fun <reified T> mock(): T =
  Mockito.mock(T::class.java)

inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> =
  ArgumentCaptor.forClass(T::class.java)

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T =
  argumentCaptor.capture()

fun <T> OngoingStubbing<Result<T>>.thenSuccess(value: T): OngoingStubbing<Result<T>> =
  thenReturn(Result.success(value))

fun <T> OngoingStubbing<Result<T>>.thenFailure(exception: Throwable): OngoingStubbing<Result<T>> =
  thenReturn(Result.failure(exception))
