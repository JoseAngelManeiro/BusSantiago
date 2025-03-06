package org.galio.bussantiago.core

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.domain.model.LineDetails

interface GetLineDetails : Interactor<Int, LineDetails>
