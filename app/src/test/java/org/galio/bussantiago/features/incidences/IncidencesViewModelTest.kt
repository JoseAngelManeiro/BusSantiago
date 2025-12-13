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
  private val observer = mock<Observer<Resource<List<Incidence>>>>()

  private lateinit var viewModel: IncidencesViewModel

  private val lineId = 123

  @Before
  fun setUp() {
    viewModel = IncidencesViewModel(executor, getLineIncidences)
    viewModel.incidences.observeForever(observer)
  }

  @Test
  fun `if all goes well, the incidences are loaded sorted by startDate descending`() {
    val oldDate = Calendar.getInstance().apply { set(2025, 11, 10) }.time
    val middleDate = Calendar.getInstance().apply { set(2025, 11, 11) }.time
    val recentDate = Calendar.getInstance().apply { set(2025, 11, 12) }.time

    val incidence1 = createIncidence(id = 1, startDate = oldDate)
    val incidence2 = createIncidence(id = 2, startDate = recentDate)
    val incidence3 = createIncidence(id = 3, startDate = middleDate)

    val incidencesStub = listOf(incidence1, incidence2, incidence3)
    whenever(getLineIncidences(lineId)).thenSuccess(incidencesStub)

    viewModel.loadIncidences(lineId)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(
      Resource.success(listOf(incidence2, incidence3, incidence1))
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

  private fun createIncidence(
    id: Int = 1,
    title: String = "Any title",
    description: String = "Any description",
    startDate: java.util.Date? = Calendar.getInstance().time
  ): Incidence {
    return Incidence(
      id = id,
      title = title,
      description = description,
      startDate = startDate,
      endDate = null
    )
  }
}
