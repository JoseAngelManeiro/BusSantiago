package org.galio.bussantiago.features.mapmarker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.galio.bussantiago.common.model.LineUiModel
import org.galio.bussantiago.databinding.SynopticBadgeItemBinding
import org.galio.bussantiago.shared.SynopticModel

class LinesBadgeAdapter(
  private val lines: List<LineUiModel>
) : RecyclerView.Adapter<LinesBadgeAdapter.ViewHolder>() {

  class ViewHolder(
    val binding: SynopticBadgeItemBinding
  ) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = SynopticBadgeItemBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val line = lines[position]
    holder.binding.root.render(
      SynopticModel(synoptic = line.synoptic, style = line.style)
    )
  }

  override fun getItemCount() = lines.size
}
