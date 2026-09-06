package org.galio.bussantiago.features.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.galio.bussantiago.common.BaseBottomSheetDialogFragment
import org.galio.bussantiago.databinding.FavoritesDialogFragmentBinding
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    viewModel.favoriteModels.observe(viewLifecycleOwner) { resource ->
      resource.fold { busStopFavorites ->
        with(binding) {
          if (busStopFavorites.isEmpty()) {
            noFavoritesTextView.visibility = View.VISIBLE
          } else {
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
