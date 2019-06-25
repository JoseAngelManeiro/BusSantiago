package org.galio.bussantiago.lines

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class LinesViewModel(getLines: GetLines) : ViewModel() {

  private val liveString = MutableLiveData<String>()

  fun getLiveString() = liveString

  init {
    liveString.postValue(getLines.invoke().toString())
  }
}
