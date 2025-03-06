package org.galio.bussantiago.core

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.domain.model.Line

interface GetLines : Interactor<Unit, List<Line>>
