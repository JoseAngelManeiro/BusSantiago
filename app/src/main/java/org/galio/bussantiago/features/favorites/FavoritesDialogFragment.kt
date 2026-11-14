package org.galio.bussantiago.features.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import org.galio.bussantiago.R
import org.galio.bussantiago.common.BaseBottomSheetDialogFragment
import org.galio.bussantiago.common.SwipeToDeleteCallback
import org.galio.bussantiago.databinding.FavoritesDialogFragmentBinding
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesDialogFragment : BaseBottomSheetDialogFragment() {

  companion object {
    private const val UNDO_TIMEOUT_MS = 3000L
  }

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
            favoritesRecyclerView.adapter = BusStopFavoritesAdapter(
              items = busStopFavorites,
              onUndoListener = { busStopFavorite ->
                viewModel.addBusStopFavorite(busStopFavorite)
              },
              listener = {
                viewModel.onBusStopFavoriteClick(it)
                dismiss()
              }
            )
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
    val swipeHandler = SwipeToDeleteCallback(
      context = requireContext(),
      iconResId = R.drawable.ic_delete,
      backgroundColorResId = R.color.swipe_delete_background,
      isItemSwipable = { position ->
        val adapter = binding.favoritesRecyclerView.adapter as? BusStopFavoritesAdapter
        adapter != null && !adapter.isUndoItem(position)
      },
      onSwipedAction = { position, _ ->
        val bindingRef = _binding ?: return@SwipeToDeleteCallback
        val adapter = bindingRef.favoritesRecyclerView.adapter as? BusStopFavoritesAdapter ?: return@SwipeToDeleteCallback
        val deletedItem = adapter.showUndo(position) ?: return@SwipeToDeleteCallback
        viewModel.removeBusStopFavorite(deletedItem)

        view?.postDelayed({
          val currentBinding = _binding ?: return@postDelayed
          val currentAdapter = currentBinding.favoritesRecyclerView.adapter as? BusStopFavoritesAdapter ?: return@postDelayed
          val undoPos = currentAdapter.findUndoPosition(deletedItem)
          if (undoPos != null) {
            currentAdapter.removePermanently(undoPos)
            if (!currentAdapter.hasVisibleFavorites()) {
              currentBinding.noFavoritesTextView.visibility = View.VISIBLE
            }
          }
        }, UNDO_TIMEOUT_MS)
      }
    )
    ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.favoritesRecyclerView)
  }

  override fun onDestroyView() {
    _binding?.root?.removeCallbacks(null)
    super.onDestroyView()
    _binding = null
  }
}
