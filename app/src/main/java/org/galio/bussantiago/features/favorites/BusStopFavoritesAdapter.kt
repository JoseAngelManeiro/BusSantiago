package org.galio.bussantiago.features.favorites

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import org.galio.bussantiago.domain.model.BusStopFavorite

class BusStopFavoritesAdapter(
  private val items: List<BusStopFavorite>,
  private val listener: (BusStopFavorite) -> Unit
) : RecyclerView.Adapter<BusStopFavoritesAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val itemViewHolder = parent.inflate(R.layout.busstop_item)
    return ItemViewHolder(itemViewHolder)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val codeView = itemView.findViewById(R.id.codeTextView) as TextView
    private val nameView = itemView.findViewById(R.id.nameTextView) as TextView

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition])
      }
    }

    fun bind(busStopFavorite: BusStopFavorite) {
      codeView.text = busStopFavorite.code
      nameView.text = busStopFavorite.name
    }
  }
}
