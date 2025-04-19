package org.galio.bussantiago.features.favorites

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.databinding.FavoritesDialogFragmentBinding
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesDialogFragment(
  private val onBusStopClicked: (BusStopModel) -> Unit
) : BottomSheetDialogFragment() {

  private var _binding: FavoritesDialogFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: FavoritesViewModel by viewModel()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return super.onCreateDialog(savedInstanceState).apply {
      // Set the opacity level of the parent shadow behind dialog (1.0f is completely black)
      window?.setDimAmount(0.3f)

      // Set transparency to dialog layer
      setOnShowListener {
        val bottomSheet = findViewById<View>(
          com.google.android.material.R.id.design_bottom_sheet
        ) as FrameLayout
        bottomSheet.setBackgroundResource(android.R.color.transparent)
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FavoritesDialogFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.favoriteModels.observe(viewLifecycleOwner) { resource ->
      resource.fold { busStopFavorites ->
        with(binding) {
          if (busStopFavorites.isEmpty()) {
            noFavoritesTextView.visibility = View.VISIBLE
          } else {
            favoritesRecyclerView.adapter =
              BusStopFavoritesAdapter(busStopFavorites) { onBusStopFavoriteClick(it) }
          }
        }
      }
    }

    viewModel.loadFavorites()
  }

  private fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
    dismiss()
    onBusStopClicked(BusStopModel(busStopFavorite.code, busStopFavorite.name))
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
