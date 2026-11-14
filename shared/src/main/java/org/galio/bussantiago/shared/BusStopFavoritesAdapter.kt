package org.galio.bussantiago.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.core.model.BusStopFavorite

sealed class FavoriteRow {
  data class Content(val favorite: BusStopFavorite) : FavoriteRow()
  data class Undo(val favorite: BusStopFavorite) : FavoriteRow()
}

class BusStopFavoritesAdapter(
  items: List<BusStopFavorite>,
  private val onUndoListener: ((BusStopFavorite) -> Unit)? = null,
  private val listener: (BusStopFavorite) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    private const val VIEW_TYPE_CONTENT = 0
    private const val VIEW_TYPE_UNDO = 1
  }

  private val rows: MutableList<FavoriteRow> = items.map { FavoriteRow.Content(it) }.toMutableList()

  override fun getItemViewType(position: Int): Int {
    return when (rows[position]) {
      is FavoriteRow.Content -> VIEW_TYPE_CONTENT
      is FavoriteRow.Undo -> VIEW_TYPE_UNDO
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == VIEW_TYPE_UNDO) {
      val itemViewHolder = LayoutInflater.from(parent.context)
        .inflate(R.layout.busstop_undo_item, parent, false)
      UndoViewHolder(itemViewHolder)
    } else {
      val itemViewHolder = LayoutInflater.from(parent.context)
        .inflate(R.layout.busstop_item, parent, false)
      ItemViewHolder(itemViewHolder)
    }
  }

  override fun getItemCount() = rows.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (val row = rows[position]) {
      is FavoriteRow.Content -> (holder as ItemViewHolder).bind(row.favorite)
      is FavoriteRow.Undo -> (holder as UndoViewHolder).bind(row.favorite)
    }
  }

  fun getItem(position: Int): BusStopFavorite? {
    if (position !in rows.indices) return null
    return when (val row = rows[position]) {
      is FavoriteRow.Content -> row.favorite
      is FavoriteRow.Undo -> row.favorite
    }
  }

  fun isUndoItem(position: Int): Boolean {
    return position in rows.indices && rows[position] is FavoriteRow.Undo
  }

  fun showUndo(position: Int): BusStopFavorite? {
    if (position !in rows.indices) return null
    val current = rows[position] as? FavoriteRow.Content ?: return null
    rows[position] = FavoriteRow.Undo(current.favorite)
    notifyItemChanged(position)
    return current.favorite
  }

  fun restore(position: Int): BusStopFavorite? {
    if (position !in rows.indices) return null
    val current = rows[position] as? FavoriteRow.Undo ?: return null
    rows[position] = FavoriteRow.Content(current.favorite)
    notifyItemChanged(position)
    return current.favorite
  }

  fun removePermanently(position: Int): BusStopFavorite? {
    if (position !in rows.indices) return null
    val removed = rows.removeAt(position)
    notifyItemRemoved(position)
    return when (removed) {
      is FavoriteRow.Content -> removed.favorite
      is FavoriteRow.Undo -> removed.favorite
    }
  }

  fun findUndoPosition(favorite: BusStopFavorite): Int? {
    val index = rows.indexOfFirst { it is FavoriteRow.Undo && it.favorite == favorite }
    return if (index != -1) index else null
  }

  fun hasVisibleFavorites(): Boolean {
    return rows.isNotEmpty()
  }

  fun removeItem(position: Int): BusStopFavorite? = removePermanently(position)

  fun addItem(position: Int, item: BusStopFavorite) {
    val targetPosition = position.coerceIn(0, rows.size)
    rows.add(targetPosition, FavoriteRow.Content(item))
    notifyItemInserted(targetPosition)
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val codeView: TextView = itemView.findViewById(R.id.codeTextView)
    private val nameView: TextView = itemView.findViewById(R.id.nameTextView)

    init {
      itemView.setOnClickListener {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION && position in rows.indices) {
          (rows[position] as? FavoriteRow.Content)?.let {
            listener(it.favorite)
          }
        }
      }
    }

    fun bind(busStopFavorite: BusStopFavorite) {
      itemView.translationX = 0f
      itemView.alpha = 1f
      codeView.text = busStopFavorite.code
      nameView.text = busStopFavorite.name
    }
  }

  inner class UndoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val undoButton: TextView = itemView.findViewById(R.id.undoButton)

    fun bind(busStopFavorite: BusStopFavorite) {
      itemView.translationX = 0f
      itemView.alpha = 1f
      undoButton.setOnClickListener {
        val pos = adapterPosition
        if (pos != RecyclerView.NO_POSITION && isUndoItem(pos)) {
          restore(pos)
          onUndoListener?.invoke(busStopFavorite)
        }
      }
    }
  }
}
