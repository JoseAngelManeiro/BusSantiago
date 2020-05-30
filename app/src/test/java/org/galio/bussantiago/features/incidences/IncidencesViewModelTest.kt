package org.galio.bussantiago.features.incidences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.interactor.GetLineIncidences
import org.galio.bussantiago.domain.model.Incidence
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.util.Calendar

class IncidencesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = SyncInteractorExecutor()
  private val getLineIncidences = mock<GetLineIncidences>()
  private val observer = mock<Observer<Resource<List<String>>>>()

  private lateinit var viewModel: IncidencesViewModel

  private val lineId = 123

  @Before
  fun setUp() {
    viewModel = IncidencesViewModel(executor, getLineIncidences)
    viewModel.setArgs(lineId)
    viewModel.incidences.observeForever(observer)
  }

  @Test
  fun `if all goes well, the incidences' descriptions are loaded correctly`() {
    val incidencesStub = listOf(
      createIncidence(description = "Incidence 1"),
      createIncidence(description = "Incidence 2")
    )
    `when`(getLineIncidences(lineId)).thenReturn(Either.Right(incidencesStub))

    viewModel.loadIncidences()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success(listOf("Incidence 1", "Incidence 2")))
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    `when`(getLineIncidences(lineId)).thenReturn(Either.Left(exception))

    viewModel.loadIncidences()

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
