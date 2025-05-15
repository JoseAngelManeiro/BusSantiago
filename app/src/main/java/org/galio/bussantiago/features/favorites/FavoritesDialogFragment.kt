package org.galio.bussantiago.features.favorites

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.galio.bussantiago.databinding.FavoritesDialogFragmentBinding
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.R as androidR
import com.google.android.material.R as materialR

class FavoritesDialogFragment : BottomSheetDialogFragment() {

  private var _binding: FavoritesDialogFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: FavoritesViewModel by viewModel()
  private val navigator: Navigator by lazy { Navigator(this) }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return super.onCreateDialog(savedInstanceState).apply {
      // Set the opacity level of the parent shadow behind dialog (1.0f is completely black)
      window?.setDimAmount(0.3f)

      setOnShowListener {
        // Set transparency to dialog layer
        val bottomSheet = findViewById<View>(materialR.id.design_bottom_sheet) as FrameLayout
        bottomSheet.setBackgroundResource(androidR.color.transparent)
        // Expand the dialog to show all the content
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
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

    viewModel.loadFavorites()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
