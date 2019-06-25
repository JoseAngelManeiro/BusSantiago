package org.galio.bussantiago.domain

interface LineRepository {
  fun getLines(): List<String>
}
