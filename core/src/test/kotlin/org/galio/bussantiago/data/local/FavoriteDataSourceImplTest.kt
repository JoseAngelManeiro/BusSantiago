package org.galio.bussantiago.data.local

import androidx.test.core.app.ApplicationProvider
import org.galio.bussantiago.core.model.BusStopFavorite
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoriteDataSourceImplTest {

  private lateinit var favoriteDataSource: FavoriteDataSourceImpl

  @Before
  fun setUp() {
    favoriteDataSource = FavoriteDataSourceImpl(ApplicationProvider.getApplicationContext())
  }

  @After
  fun tearDown() {
    favoriteDataSource.close()
  }

  @Test
  fun `when there is no favorites saved should return an empty list`() {
    assertEquals(emptyList<BusStopFavorite>(), favoriteDataSource.getAll())
  }

  @Test
  fun `when there are some values saved should return those ones`() {
    val busStopFavorite1 = BusStopFavorite("1", "Name1")
    val busStopFavorite2 = BusStopFavorite("2", "Name2")

    favoriteDataSource.save(busStopFavorite1)
    favoriteDataSource.save(busStopFavorite2)

    val favoritesExpected = listOf(busStopFavorite1, busStopFavorite2)
    assertEquals(favoritesExpected, favoriteDataSource.getAll())
  }

  @Test
  fun `when a value is removed should not be returned anymore`() {
    val busStopFavorite1 = BusStopFavorite("1", "Name1")
    val busStopFavorite2 = BusStopFavorite("2", "Name2")

    favoriteDataSource.save(busStopFavorite1)
    favoriteDataSource.save(busStopFavorite2)

    favoriteDataSource.remove(busStopFavorite1)

    val favoritesExpected = listOf(busStopFavorite2)
    assertEquals(favoritesExpected, favoriteDataSource.getAll())
  }
}
