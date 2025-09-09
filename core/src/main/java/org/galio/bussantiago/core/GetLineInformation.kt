package org.galio.bussantiago.core

interface GetLineInformation {
  operator fun invoke(lineId: Int): Result<String>
}
