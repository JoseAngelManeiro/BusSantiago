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

  private val _lines = MutableLiveData<Resource<List<LineModel>>>()

  val lines: LiveData<Resource<List<LineModel>>>
    get() = _lines

  init {
    _lines.value = Resource.loading()
    executor(
      getLines,
      Unit,
      onSuccess = {
        _lines.value = Resource.success(
          it.map { line ->
            LineModel(
              id = line.id,
              synopticModel = SynopticModel(
                synoptic = line.synoptic,
                style = line.style
              ),
              name = line.name
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
