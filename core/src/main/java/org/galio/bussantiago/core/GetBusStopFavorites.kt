package org.galio.bussantiago.core

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.domain.model.BusStopFavorite

interface GetBusStopFavorites : Interactor<Unit, List<BusStopFavorite>>
