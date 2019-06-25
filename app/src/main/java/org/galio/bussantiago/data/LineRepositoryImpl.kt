package org.galio.bussantiago.data

import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.domain.LineRepository

internal class LineRepositoryImpl(
  private val apiClient: ApiClient
) : LineRepository {

  override fun getLines(): List<String> {
    return listOf("Line1", "Line2")
  }
}
