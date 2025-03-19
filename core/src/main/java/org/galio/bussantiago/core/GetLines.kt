package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.Line

interface GetLines : Interactor<Unit, List<Line>>
