package org.galio.bussantiago.data.mapper

interface Mapper<DataModel, DomainModel> {
  fun toDomain(dataModel: DataModel): DomainModel
}
