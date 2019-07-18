package org.galio.bussantiago.lines

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate

class LinesAdapter(
  private val items: List<LineModel>,
  private val listener: (Int) -> Unit
) : RecyclerView.Adapter<LinesAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val itemViewHolder = parent.inflate(R.layout.line_item)
    return ItemViewHolder(itemViewHolder)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val synoptic = itemView.findViewById(R.id.synopticView) as SynopticView
    private val name = itemView.findViewById(R.id.nameTextView) as TextView

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition].id)
      }
    }

    fun bind(lineModel: LineModel) {
      synoptic.render(lineModel.synopticModel)
      name.text = lineModel.name
    }
  }
}
