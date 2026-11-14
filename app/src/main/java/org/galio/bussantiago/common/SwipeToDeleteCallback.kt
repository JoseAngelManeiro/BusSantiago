package org.galio.bussantiago.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.withClip
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

open class SwipeToDeleteCallback(
  context: Context,
  @DrawableRes iconResId: Int,
  @ColorRes backgroundColorResId: Int,
  private val isItemSwipable: (position: Int) -> Boolean = { true },
  private val onSwipedAction: (position: Int, direction: Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

  private val deleteIcon: Drawable? = ContextCompat.getDrawable(context, iconResId)
  private val deleteBackground: ColorDrawable = ContextCompat.getColor(context, backgroundColorResId).toDrawable()

  override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
    val position = viewHolder.adapterPosition
    if (position == RecyclerView.NO_POSITION || !isItemSwipable(position)) {
      return 0
    }
    return super.getSwipeDirs(recyclerView, viewHolder)
  }

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean = false

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    val position = viewHolder.adapterPosition
    if (position != RecyclerView.NO_POSITION) {
      onSwipedAction(position, direction)
    }
  }

  override fun onChildDraw(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    val position = viewHolder.adapterPosition
    if (position == RecyclerView.NO_POSITION || !isItemSwipable(position)) {
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
      return
    }

    val itemView = viewHolder.itemView
    val itemHeight = itemView.bottom - itemView.top

    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
      if (dX > 0) { // Swiping right
        val right = itemView.left + dX.toInt()
        deleteBackground.setBounds(itemView.left, itemView.top, right, itemView.bottom)
        deleteBackground.draw(c)

        deleteIcon?.let { icon ->
          val iconMargin = (itemHeight - icon.intrinsicHeight) / 2
          val iconTop = itemView.top + iconMargin
          val iconBottom = iconTop + icon.intrinsicHeight
          val iconLeft = itemView.left + iconMargin
          val iconRight = iconLeft + icon.intrinsicWidth
          icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

          c.withClip(itemView.left, itemView.top, right, itemView.bottom) {
            icon.draw(this)
          }
        }
      } else if (dX < 0) { // Swiping left
        val left = itemView.right + dX.toInt()
        deleteBackground.setBounds(left, itemView.top, itemView.right, itemView.bottom)
        deleteBackground.draw(c)

        deleteIcon?.let { icon ->
          val iconMargin = (itemHeight - icon.intrinsicHeight) / 2
          val iconTop = itemView.top + iconMargin
          val iconBottom = iconTop + icon.intrinsicHeight
          val iconRight = itemView.right - iconMargin
          val iconLeft = iconRight - icon.intrinsicWidth
          icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

          c.withClip(left, itemView.top, itemView.right, itemView.bottom) {
            icon.draw(this)
          }
        }
      }
    }

    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
  }
}
