package org.galio.bussantiago.features.menu

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate

class MenuAdapter(
  private val items: List<MenuOptionModel>,
  private val menuTextUtils: MenuTextUtils,
  private val listener: (MenuOptionModel) -> Unit
) : RecyclerView.Adapter<MenuAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val itemViewHolder = parent.inflate(R.layout.menu_item)
    return ItemViewHolder(itemViewHolder)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val context = itemView.context
    private val iconView: ImageView = itemView.findViewById(R.id.iconImageView)
    private val nameView: TextView = itemView.findViewById(R.id.titleTextView)

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition])
      }
    }

    fun bind(menuOptionModel: MenuOptionModel) {
      iconView.setImageResource(menuTextUtils.getIconResource(menuOptionModel.menuType))
      iconView.contentDescription =
        menuTextUtils.getContentDescription(context, menuOptionModel.menuType)
      nameView.text = menuTextUtils.getTitle(context, menuOptionModel)
    }
  }
}
