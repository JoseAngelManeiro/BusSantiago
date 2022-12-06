package org.galio.bussantiago.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.galio.bussantiago.executor.InteractorExecutor

/**
* Base class that helps to isolate the process of scope initialization.
* */
open class BaseViewModel(
  interactorExecutor: InteractorExecutor
) : ViewModel() {

  init {
    interactorExecutor.setViewModelScope(viewModelScope)
  }
}
