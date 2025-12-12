package org.galio.bussantiago.features.lines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.core.GetLines
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.shared.SynopticModel

class LinesViewModel(
  private val executor: UseCaseExecutor,
  private val getLines: GetLines
) : BaseViewModel(executor) {

  private val _navigationEvent = SingleLiveEvent<NavScreen>()
  private val _lineModels = MutableLiveData<Resource<List<LineModel>>>()

  val lineModels: LiveData<Resource<List<LineModel>>>
    get() = _lineModels

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  fun loadLines() {
    _lineModels.value = Resource.loading()
    executor(
      useCase = { getLines() },
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
      onError = { _lineModels.value = Resource.error(it) }
    )
  }

  fun onLineClicked(lineId: Int) {
    _navigationEvent.value = NavScreen.LineMenu(lineId)
  }
}
