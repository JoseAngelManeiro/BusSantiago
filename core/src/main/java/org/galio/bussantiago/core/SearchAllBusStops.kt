package org.galio.bussantiago.core

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.domain.model.BusStopSearch

interface SearchAllBusStops : Interactor<Unit, List<BusStopSearch>>
