package org.galio.bussantiago.util

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
