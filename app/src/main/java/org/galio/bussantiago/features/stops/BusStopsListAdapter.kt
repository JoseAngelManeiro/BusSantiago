package org.galio.bussantiago.features.stops

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import org.galio.bussantiago.common.model.BusStopModel

class BusStopsListAdapter(
  private val items: List<BusStopModel>,
  private val listener: (BusStopModel) -> Unit
) : RecyclerView.Adapter<BusStopsListAdapter.ItemViewHolder>() {

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

    fun bind(busStopModel: BusStopModel) {
      codeView.text = busStopModel.code
      nameView.text = busStopModel.name
    }
  }
}
