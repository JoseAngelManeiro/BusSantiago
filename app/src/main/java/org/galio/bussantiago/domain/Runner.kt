package org.galio.bussantiago.domain

interface Runner {
  operator fun invoke(c: () -> Unit)
}
