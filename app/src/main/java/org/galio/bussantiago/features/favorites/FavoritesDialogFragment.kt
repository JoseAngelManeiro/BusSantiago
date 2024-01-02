package org.galio.bussantiago.features.favorites

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.favorites_dialog_fragment.favoritesRecyclerView
import kotlinx.android.synthetic.main.favorites_dialog_fragment.noFavoritesTextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.koin.android.ext.android.inject

class FavoritesDialogFragment(
  private val onBusStopClicked: (BusStopModel) -> Unit
) : BottomSheetDialogFragment() {

  private val favoriteDataSource: FavoriteDataSource by inject()

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
  ): View? {
    return inflater.inflate(R.layout.favorites_dialog_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val busStopFavorites = favoriteDataSource.getAll()
    if (busStopFavorites.isEmpty()) {
      noFavoritesTextView.visibility = View.VISIBLE
    } else {
      favoritesRecyclerView.adapter =
        BusStopFavoritesAdapter(busStopFavorites) { onBusStopFavoriteClick(it) }
    }
  }

  private fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
    dismiss()
    onBusStopClicked(BusStopModel(busStopFavorite.code, busStopFavorite.name))
  }
}
