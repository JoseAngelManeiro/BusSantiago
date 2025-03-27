package org.galio.bussantiago.features.times

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import org.galio.bussantiago.common.model.SynopticView
import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.getDescriptionByMinutes

class TimesAdapter(
  private val items: List<LineRemainingTimeModel>
) : RecyclerView.Adapter<TimesAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val itemViewHolder = parent.inflate(R.layout.time_item)
    return ItemViewHolder(itemViewHolder)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val synopticView: SynopticView = itemView.findViewById(R.id.synopticView)
    private val timeView: TextView = itemView.findViewById(R.id.timeTextView)

    fun bind(lineRemainingTimeModel: LineRemainingTimeModel) {
      synopticView.render(lineRemainingTimeModel.synopticModel)
      timeView.text = getDescriptionByMinutes(lineRemainingTimeModel.minutesUntilNextArrival)
    }
  }
}
