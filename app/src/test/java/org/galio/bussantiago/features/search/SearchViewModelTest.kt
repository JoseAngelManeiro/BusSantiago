package org.galio.bussantiago.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.interactor.SearchBusStop
import org.galio.bussantiago.domain.model.Coordinates
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.model.NullBusStopSearch
import org.galio.bussantiago.exception.NetworkConnectionException
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify

class SearchViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val searchBusStop = mock<SearchBusStop>()
  private val observer = mock<Observer<Resource<BusStopModel>>>()

  private lateinit var searchViewModel: SearchViewModel

  private val busStopCode = "228"

  @Before
  fun setUp() {
    searchViewModel = SearchViewModel(executor, searchBusStop)
    searchViewModel.busStopModel.observeForever(observer)
  }

  @Test
  fun `load a bus stop when interactor finds a value`() {
    val busStopSearchStub = createSearchBusStop(
      code = busStopCode,
      name = "Ensinanza"
    )
    given(searchBusStop(busStopCode)).willReturn(Either.right(busStopSearchStub))

    searchViewModel.search(busStopCode.toInt())

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success(
      BusStopModel(busStopCode, "Ensinanza"))
    )
  }

  @Test
  fun `load null when interactor doesn't find a value`() {
    given(searchBusStop(busStopCode))
      .willReturn(Either.right(NullBusStopSearch()))

    searchViewModel.search(busStopCode.toInt())

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success(BusStopModel("", "")))
  }

  @Test
  fun `fire the exception received`() {
    val exception = NetworkConnectionException()
    given(searchBusStop(busStopCode)).willReturn(Either.left(exception))

    searchViewModel.search(busStopCode.toInt())

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }

  private fun createSearchBusStop(code: String, name: String) =
    BusStopSearch(
      id = 1,
      code = code,
      name = name,
      zone = null,
      coordinates = Coordinates(44.5, 11.2),
      lines = mock()
    )
}
