package org.galio.bussantiago.features.lines

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import org.galio.bussantiago.common.model.SynopticView

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
    private val synopticView: SynopticView = itemView.findViewById(R.id.synopticView)
    private val nameView: TextView = itemView.findViewById(R.id.nameTextView)
    private val incidentsImageView: ImageView = itemView.findViewById(R.id.incidentsImageView)

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition].id)
      }
    }

    fun bind(lineModel: LineModel) {
      synopticView.render(lineModel.synopticModel)
      nameView.text = lineModel.name
      incidentsImageView.visibility = if (lineModel.incidents > 0) View.VISIBLE else View.GONE
    }
  }
}
