package org.galio.bussantiago.features.favorites

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.information_fragment.progressBar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.features.BaseHomeFragment
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseHomeFragment() {

  private val viewModel: FavoritesViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.favorites_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initActionBar(title = getString(R.string.favorites))

    viewModel.busStopFavorites.observe(viewLifecycleOwner, Observer {
      it?.let { resourceBusStopFavorites ->
        when (resourceBusStopFavorites.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            val busStopFavorites = resourceBusStopFavorites.data
            if (busStopFavorites.isNullOrEmpty()) {
              noFavoritesTextView.visibility = View.VISIBLE
            } else {
              favoritesRecyclerView.adapter =
                BusStopFavoritesAdapter(busStopFavorites) { onBusStopFavoriteClick(it) }
            }
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceBusStopFavorites.exception!!) { viewModel.loadFavorites() }
          }
        }
      }
    })

    viewModel.loadFavorites()
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }

  private fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
    navigateSafe(
      R.id.actionShowTimesFragment,
      TimesFragment.createArguments(BusStopModel(busStopFavorite.code, busStopFavorite.name))
    )
  }
}
