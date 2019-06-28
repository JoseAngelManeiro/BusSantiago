package org.galio.bussantiago.domain

interface AppExecutors {
  val background: Runner
  val main: Runner
}
