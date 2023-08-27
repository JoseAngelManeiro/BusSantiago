package org.galio.bussantiago.features.lines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.SynopticModel
import org.galio.bussantiago.domain.interactor.GetLines
import org.galio.bussantiago.executor.InteractorExecutor

class LinesViewModel(
  private val executor: InteractorExecutor,
  private val getLines: GetLines
) : BaseViewModel(executor) {

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
              name = line.name,
              incidents = line.incidents
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
