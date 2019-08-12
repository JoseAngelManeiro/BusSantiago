package org.galio.bussantiago.features.times

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import org.galio.bussantiago.common.ui.SynopticView

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
    private val synopticView = itemView.findViewById(R.id.synopticView) as SynopticView
    private val timeView = itemView.findViewById(R.id.timeTextView) as TextView

    fun bind(lineRemainingTimeModel: LineRemainingTimeModel) {
      synopticView.render(lineRemainingTimeModel.synopticModel)
      timeView.text = getDescriptionByMinutes(lineRemainingTimeModel.minutesUntilNextArrival)
    }
  }
}
