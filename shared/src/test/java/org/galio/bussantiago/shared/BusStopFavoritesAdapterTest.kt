package org.galio.bussantiago.shared

import org.galio.bussantiago.core.model.BusStopFavorite
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.recyclerview.widget.RecyclerView

@RunWith(RobolectricTestRunner::class)
class BusStopFavoritesAdapterTest {

  private val item1 = BusStopFavorite("1", "Stop 1")
  private val item2 = BusStopFavorite("2", "Stop 2")
  private val item3 = BusStopFavorite("3", "Stop 3")

  @Test
  fun `getItemCount returns correct size`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item2)) {}

    assertEquals(2, adapter.itemCount)
  }

  @Test
  fun `getItem returns correct item for valid position`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item2)) {}

    assertEquals(item1, adapter.getItem(0))
    assertEquals(item2, adapter.getItem(1))
  }

  @Test
  fun `getItem returns null for invalid position`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1)) {}

    assertNull(adapter.getItem(-1))
    assertNull(adapter.getItem(1))
  }

  @Test
  fun `removeItem removes item and updates count`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item2)) {}
    var removedPosition = -1
    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        removedPosition = positionStart
      }
    })

    val removed = adapter.removeItem(0)

    assertEquals(item1, removed)
    assertEquals(1, adapter.itemCount)
    assertEquals(item2, adapter.getItem(0))
    assertEquals(0, removedPosition)
  }

  @Test
  fun `removeItem returns null for invalid position`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1)) {}

    val removed = adapter.removeItem(5)

    assertNull(removed)
    assertEquals(1, adapter.itemCount)
  }

  @Test
  fun `addItem adds item at position and updates count`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item3)) {}
    var insertedPosition = -1
    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        insertedPosition = positionStart
      }
    })

    adapter.addItem(1, item2)

    assertEquals(3, adapter.itemCount)
    assertEquals(item1, adapter.getItem(0))
    assertEquals(item2, adapter.getItem(1))
    assertEquals(item3, adapter.getItem(2))
    assertEquals(1, insertedPosition)
  }

  @Test
  fun `addItem with out of bounds position coerces position safely`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1)) {}
    var insertedPosition = -1
    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        insertedPosition = positionStart
      }
    })

    adapter.addItem(100, item2)

    assertEquals(2, adapter.itemCount)
    assertEquals(item2, adapter.getItem(1))
    assertEquals(1, insertedPosition)
  }

  @Test
  fun `showUndo marks item as undo and notifies change`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item2)) {}
    var changedPos = -1
    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        changedPos = positionStart
      }
    })

    val undoItem = adapter.showUndo(0)

    assertEquals(item1, undoItem)
    assertEquals(true, adapter.isUndoItem(0))
    assertEquals(false, adapter.isUndoItem(1))
    assertEquals(0, changedPos)
  }

  @Test
  fun `restore marks undo item as normal content and notifies change`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item2)) {}
    adapter.showUndo(0)

    var changedPos = -1
    adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
      override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        changedPos = positionStart
      }
    })

    val restoredItem = adapter.restore(0)

    assertEquals(item1, restoredItem)
    assertEquals(false, adapter.isUndoItem(0))
    assertEquals(0, changedPos)
  }

  @Test
  fun `findUndoPosition returns correct index for undo item`() {
    val adapter = BusStopFavoritesAdapter(listOf(item1, item2, item3)) {}
    adapter.showUndo(1)

    assertEquals(1, adapter.findUndoPosition(item2))
    assertNull(adapter.findUndoPosition(item1))
  }
}
