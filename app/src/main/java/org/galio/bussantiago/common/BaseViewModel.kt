package org.galio.bussantiago.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.galio.bussantiago.executor.UseCaseExecutor

/**
 * Base class that helps to isolate the process of scope initialization.
 * */
open class BaseViewModel(
  useCaseExecutor: UseCaseExecutor
) : ViewModel() {

  init {
    useCaseExecutor.setViewModelScope(viewModelScope)
  }
}
