package org.galio.bussantiago.features.incidences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.util.TestUseCaseExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import java.util.Calendar

class IncidencesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineIncidences = mock<GetLineIncidences>()
  private val observer = mock<Observer<Resource<List<String>>>>()

  private lateinit var viewModel: IncidencesViewModel

  private val lineId = 123

  @Before
  fun setUp() {
    viewModel = IncidencesViewModel(executor, getLineIncidences)
    viewModel.incidences.observeForever(observer)
  }

  @Test
  fun `if all goes well, the incidences descriptions are loaded correctly in reverse order`() {
    val incidencesStub = listOf(
      createIncidence(description = "Incidence 1"),
      createIncidence(description = "Incidence 2"),
      createIncidence(description = "Incidence 3")
    )
    whenever(getLineIncidences(lineId)).thenSuccess(incidencesStub)

    viewModel.loadIncidences(lineId)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(
      Resource.success(listOf("Incidence 3", "Incidence 2", "Incidence 1"))
    )
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    whenever(getLineIncidences(lineId)).thenFailure(exception)

    viewModel.loadIncidences(lineId)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }

  private fun createIncidence(description: String): Incidence {
    return Incidence(
      id = 1,
      title = "Any title",
      description = description,
      startDate = Calendar.getInstance().time,
      endDate = null
    )
  }
}
