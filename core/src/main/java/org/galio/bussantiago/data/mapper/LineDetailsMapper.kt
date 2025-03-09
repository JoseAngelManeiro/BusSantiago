package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.entity.LineDetailsEntity

internal class LineDetailsMapper(
  private val routeMapper: RouteMapper,
  private val incidenceMapper: IncidenceMapper
) : Mapper<LineDetailsEntity, LineDetails> {

  override fun toDomain(dataModel: LineDetailsEntity): LineDetails {
    return LineDetails(
      id = dataModel.id,
      code = dataModel.codigo,
      synoptic = dataModel.sinoptico,
      name = dataModel.nombre,
      information = dataModel.informacion,
      style = dataModel.estilo,
      routes = dataModel.trayectos.map { routeMapper.toDomain(it) },
      incidences = dataModel.incidencias.map { incidenceMapper.toDomain(it) }
    )
  }
}
