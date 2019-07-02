package org.galio.bussantiago.lines

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource

class LinesViewModel(getLines: GetLines) : ViewModel() {

  private val lines = MutableLiveData<Resource<List<LineView>>>()

  fun getLines(): LiveData<Resource<List<LineView>>> = lines

  init {
    lines.value = Resource.loading()
    getLines(
      request = Unit,
      onError = {
        lines.value = Resource.error(it)
      },
      onSuccess = {
        lines.value = Resource.success(
          it.map { line ->
            LineView(
              id = line.id,
              synoptic = line.synoptic,
              name = line.name,
              style = line.style
            )
          }
        )
      }
    )
  }
}
