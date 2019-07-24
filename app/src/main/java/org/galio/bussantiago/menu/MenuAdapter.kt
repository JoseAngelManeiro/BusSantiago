package org.galio.bussantiago.menu

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.inflate

class MenuAdapter(
  private val items: List<MenuOptionModel>,
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
    private val icon = itemView.findViewById(R.id.iconImageView) as ImageView
    private val name = itemView.findViewById(R.id.titleTextView) as TextView

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition])
      }
    }

    fun bind(menuOptionModel: MenuOptionModel) {
      icon.setImageResource(getIconResourceByMenuType(menuOptionModel.menuType))
      name.text = getTitle(context, menuOptionModel)
    }
  }

  private fun getIconResourceByMenuType(menuType: MenuType): Int {
    return when (menuType) {
      MenuType.OUTWARD_ROUTE -> R.drawable.ic_outward_route
      MenuType.RETURN_ROUTE -> R.drawable.ic_return_route
      MenuType.INFORMATION -> R.drawable.ic_information
      MenuType.INCIDENCES -> R.drawable.ic_incidences
    }
  }

  private fun getTitle(context: Context, menuOptionModel: MenuOptionModel): String {
    return when (menuOptionModel.menuType) {
      MenuType.INFORMATION -> context.getString(R.string.information)
      MenuType.INCIDENCES -> context.getString(R.string.incidences)
      else -> menuOptionModel.title.orEmpty()
    }
  }
}
