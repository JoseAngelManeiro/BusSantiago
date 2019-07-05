package org.galio.bussantiago.lines

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class LinesViewModel(
  executor: InteractorExecutor,
  getLines: GetLines
) : ViewModel() {

  private val _lines = MutableLiveData<Resource<List<LineView>>>()

  val lines: LiveData<Resource<List<LineView>>>
    get() = _lines

  init {
    _lines.value = Resource.loading()
    executor(
      getLines,
      Unit,
      onSuccess = {
        _lines.value = Resource.success(
          it.map { line ->
            LineView(
              id = line.id,
              synoptic = line.synoptic,
              name = line.name,
              style = line.style
            )
          }
        )
      },
      onError = {
        _lines.value = Resource.error(it)
      }
    )
  }
}
