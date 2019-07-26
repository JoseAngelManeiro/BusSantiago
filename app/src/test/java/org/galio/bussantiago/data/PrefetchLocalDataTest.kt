package org.galio.bussantiago.data

import org.junit.Assert.assertEquals
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

class PrefetchLocalDataTest {

    private val handleLoadFromLocal = mock<() -> Foo?>()
    private val handleLoadFromService = mock<() -> Either<Exception, Foo>>()
    private val handleSaveServiceResult = mock<(Foo) -> Unit>()

    private lateinit var prefetchLocalData: PrefetchLocalData<Foo, Foo>

    @Before
    fun setUp() {
        prefetchLocalData = object : PrefetchLocalData<Foo, Foo>() {
            override fun loadFromLocal(): Foo? {
                return handleLoadFromLocal()
            }

            override fun loadFromService(): Either<Exception, Foo> {
                return handleLoadFromService()
            }

            override fun saveServiceResult(item: Foo) {
                handleSaveServiceResult(item)
            }
        }
    }

    @Test
    fun `local data is always returned first if they are not null`() {
        val localData = Foo(1)
        given(handleLoadFromLocal()).willReturn(localData)

        val result = prefetchLocalData.load()

        assertTrue(result.isRight)
        assertEquals(localData, result.rightValue)
    }

    @Test
    fun `if there is no local data, it is fetched from service`() {
        val localDataFirstTime = null
        val localDataAfterSaving = Foo(1)
        val serviceData = Foo(2)
        given(handleLoadFromLocal())
            .willReturn(localDataFirstTime)
            .willReturn(localDataAfterSaving)
        given(handleLoadFromService()).willReturn(Either.Right(serviceData))

        val result = prefetchLocalData.load()

        assertTrue(result.isRight)
        verify(handleSaveServiceResult).invoke(serviceData)
        assertEquals(localDataAfterSaving, result.rightValue)
    }

    @Test
    fun `if there is no local data and the service fails, an exception is returned`() {
        val localData = null
        val serviceException = Exception("Any exception")
        given(handleLoadFromLocal()).willReturn(localData)
        given(handleLoadFromService()).willReturn(Either.Left(serviceException))

        val result = prefetchLocalData.load()

        assertTrue(result.isLeft)
        assertEquals(serviceException, result.leftValue)
    }

    private data class Foo(val value: Int)
}
