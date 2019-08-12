package org.galio.bussantiago.features.lines

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import org.galio.bussantiago.common.ui.SynopticView

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
    private val synopticView = itemView.findViewById(R.id.synopticView) as SynopticView
    private val nameView = itemView.findViewById(R.id.nameTextView) as TextView

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition].id)
      }
    }

    fun bind(lineModel: LineModel) {
      synopticView.render(lineModel.synopticModel)
      nameView.text = lineModel.name
    }
  }
}
