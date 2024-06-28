package org.galio.bussantiago.features.menu

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
    private val iconView: ImageView = itemView.findViewById(R.id.iconImageView)
    private val nameView: TextView = itemView.findViewById(R.id.titleTextView)

    init {
      itemView.setOnClickListener {
        listener(items[adapterPosition])
      }
    }

    fun bind(menuOptionModel: MenuOptionModel) {
      iconView.setImageResource(getIconResource(menuOptionModel.menuType))
      iconView.contentDescription = getContentDescription(context, menuOptionModel.menuType)
      nameView.text = getTitle(context, menuOptionModel)
    }
  }

  private fun getIconResource(menuType: MenuType): Int {
    return when (menuType) {
      MenuType.OUTWARD_ROUTE -> R.drawable.ic_outward_route
      MenuType.RETURN_ROUTE -> R.drawable.ic_return_route
      MenuType.INFORMATION -> R.drawable.ic_information
      MenuType.INCIDENCES -> R.drawable.ic_incidences
    }
  }

  private fun getContentDescription(context: Context, menuType: MenuType): String {
    return when (menuType) {
      MenuType.OUTWARD_ROUTE -> {
        if (items.find { it.menuType == MenuType.RETURN_ROUTE } != null) {
          context.getString(R.string.outward_route_stops)
        } else {
          context.getString(R.string.one_way_route_stops)
        }
      }

      MenuType.RETURN_ROUTE -> context.getString(R.string.return_route_stops)
      else -> ""
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
