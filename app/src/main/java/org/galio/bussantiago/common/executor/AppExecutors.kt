package org.galio.bussantiago.common.executor

interface AppExecutors {
  val background: Runner
  val main: Runner
}
