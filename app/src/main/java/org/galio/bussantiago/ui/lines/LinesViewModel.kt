package org.galio.bussantiago.ui.lines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.InteractorExecutor

class LinesViewModel(
  private val executor: InteractorExecutor,
  private val getLines: GetLines
) : ViewModel() {

  private val _lineModels = MutableLiveData<Resource<List<LineModel>>>()

  val lineModels: LiveData<Resource<List<LineModel>>>
    get() = _lineModels

  fun loadLines() {
    _lineModels.value = Resource.loading()
    executor(
      getLines,
      Unit,
      onSuccess = {
        _lineModels.value = Resource.success(
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
        _lineModels.value = Resource.error(it)
      }
    )
  }
}
