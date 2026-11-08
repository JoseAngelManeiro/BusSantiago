package org.galio.bussantiago.features.favorites

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.BaseBottomSheetDialogFragment
import org.galio.bussantiago.databinding.FavoritesDialogFragmentBinding
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.withClip

class FavoritesDialogFragment : BaseBottomSheetDialogFragment() {

  private var _binding: FavoritesDialogFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: FavoritesViewModel by viewModel()
  private val navigator: Navigator by lazy { Navigator(this) }

  override fun onCreateContentView(
    inflater: LayoutInflater,
    container: ViewGroup
  ): View {
    _binding = FavoritesDialogFragmentBinding.inflate(inflater, container, true)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupRecyclerView()

    viewModel.favoriteModels.observe(viewLifecycleOwner) { resource ->
      resource.fold { busStopFavorites ->
        with(binding) {
          if (busStopFavorites.isEmpty()) {
            noFavoritesTextView.visibility = View.VISIBLE
          } else {
            noFavoritesTextView.visibility = View.GONE
            favoritesRecyclerView.adapter = BusStopFavoritesAdapter(busStopFavorites) {
              viewModel.onBusStopFavoriteClick(it)
              dismiss()
            }
          }
        }
      }
    }

    viewModel.navigationEvent.observe(viewLifecycleOwner) { navScreen ->
      navigator.navigate(navScreen)
    }

    viewModel.init()
  }

  private fun setupRecyclerView() {
    val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
    val deleteBackground = ContextCompat.getColor(
      requireContext(), R.color.swipe_delete_background
    ).toDrawable()

    val swipeHandler = object : ItemTouchHelper.SimpleCallback(
      0,
      ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean = false

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val adapter = binding.favoritesRecyclerView.adapter as? BusStopFavoritesAdapter ?: return
        val deletedItem = adapter.removeItem(position) ?: return
        viewModel.removeBusStopFavorite(deletedItem)
        if (adapter.itemCount == 0) {
          binding.noFavoritesTextView.visibility = View.VISIBLE
        }

        Snackbar.make(binding.root, R.string.favorite_stop_removed, Snackbar.LENGTH_SHORT)
          .setAction(R.string.undo) {
            adapter.addItem(position, deletedItem)
            viewModel.addBusStopFavorite(deletedItem)
            binding.noFavoritesTextView.visibility = View.GONE
            binding.favoritesRecyclerView.scrollToPosition(position)
          }
          .show()
      }

      override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
      ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
          if (dX > 0) { // Swiping right
            val right = itemView.left + dX.toInt()
            deleteBackground.setBounds(itemView.left, itemView.top, right, itemView.bottom)
            deleteBackground.draw(c)

            deleteIcon?.let { icon ->
              val iconMargin = (itemHeight - icon.intrinsicHeight) / 2
              val iconTop = itemView.top + iconMargin
              val iconBottom = iconTop + icon.intrinsicHeight
              val iconLeft = itemView.left + iconMargin
              val iconRight = iconLeft + icon.intrinsicWidth
              icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

              c.withClip(itemView.left, itemView.top, right, itemView.bottom) {
                icon.draw(this)
              }
            }
          } else if (dX < 0) { // Swiping left
            val left = itemView.right + dX.toInt()
            deleteBackground.setBounds(left, itemView.top, itemView.right, itemView.bottom)
            deleteBackground.draw(c)

            deleteIcon?.let { icon ->
              val iconMargin = (itemHeight - icon.intrinsicHeight) / 2
              val iconTop = itemView.top + iconMargin
              val iconBottom = iconTop + icon.intrinsicHeight
              val iconRight = itemView.right - iconMargin
              val iconLeft = iconRight - icon.intrinsicWidth
              icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

              c.withClip(left, itemView.top, itemView.right, itemView.bottom) {
                icon.draw(this)
              }
            }
          }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
      }
    }
    ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.favoritesRecyclerView)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
