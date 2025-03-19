package org.galio.bussantiago.data.mapper

internal interface Mapper<DataModel, DomainModel> {
  fun toDomain(dataModel: DataModel): DomainModel
}
