package org.galio.bussantiago.features.incidences

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.fromHtml
import org.galio.bussantiago.common.inflate

class IncidencesAdapter(
  private val items: List<String>
) : RecyclerView.Adapter<IncidencesAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val itemViewHolder = parent.inflate(R.layout.incidence_item)
    return ItemViewHolder(itemViewHolder)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val incidenceView = itemView.findViewById(R.id.incidenceTextView) as TextView

    fun bind(incidence: String) {
      incidenceView.text = incidence.fromHtml()
    }
  }
}
