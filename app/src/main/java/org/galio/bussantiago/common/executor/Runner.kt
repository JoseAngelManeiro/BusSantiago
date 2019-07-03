package org.galio.bussantiago.common.executor

interface Runner {
  operator fun invoke(c: () -> Unit)
}
