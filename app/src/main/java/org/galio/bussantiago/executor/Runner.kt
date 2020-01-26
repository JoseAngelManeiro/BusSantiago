package org.galio.bussantiago.executor

interface Runner {
  operator fun invoke(c: () -> Unit)
}
