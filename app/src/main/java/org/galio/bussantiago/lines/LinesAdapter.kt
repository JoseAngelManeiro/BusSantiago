package org.galio.bussantiago.lines

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate
import android.graphics.Color.parseColor
import android.graphics.drawable.GradientDrawable

class LinesAdapter(
  private val items: List<LineView>,
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
    private val synoptic = itemView.findViewById(R.id.synopticTextView) as TextView
    private val name = itemView.findViewById(R.id.nameTextView) as TextView

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition].id)
      }
    }

    fun bind(lineView: LineView) {
      val synopticBackground = synoptic.background as GradientDrawable
      synopticBackground.setColor(parseColor(lineView.style))
      synoptic.text = lineView.synoptic.removePrefix("L")
      name.text = lineView.name
    }
  }
}
