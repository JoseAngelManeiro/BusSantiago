package org.galio.bussantiago.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.core.model.BusStopFavorite

class BusStopFavoritesAdapter(
  items: List<BusStopFavorite>,
  private val listener: (BusStopFavorite) -> Unit
) : RecyclerView.Adapter<BusStopFavoritesAdapter.ItemViewHolder>() {

  private val items: MutableList<BusStopFavorite> = items.toMutableList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val itemViewHolder = LayoutInflater.from(parent.context)
      .inflate(R.layout.busstop_item, parent, false)
    return ItemViewHolder(itemViewHolder)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  fun getItem(position: Int): BusStopFavorite? {
    return if (position in items.indices) items[position] else null
  }

  fun removeItem(position: Int): BusStopFavorite? {
    if (position !in items.indices) return null
    val item = items.removeAt(position)
    notifyItemRemoved(position)
    return item
  }

  fun addItem(position: Int, item: BusStopFavorite) {
    val targetPosition = position.coerceIn(0, items.size)
    items.add(targetPosition, item)
    notifyItemInserted(targetPosition)
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val codeView: TextView = itemView.findViewById(R.id.codeTextView)
    private val nameView: TextView = itemView.findViewById(R.id.nameTextView)

    init {
      itemView.setOnClickListener {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION && position in items.indices) {
          listener(items[position])
        }
      }
    }

    fun bind(busStopFavorite: BusStopFavorite) {
      codeView.text = busStopFavorite.code
      nameView.text = busStopFavorite.name
    }
  }
}
