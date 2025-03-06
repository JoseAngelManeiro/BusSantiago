package org.galio.bussantiago.core

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.domain.model.Incidence

interface GetLineIncidences : Interactor<Int, List<Incidence>>
