package org.galio.bussantiago.features.favorites

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.information_fragment.progressBar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

  private val viewModel: FavoritesViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

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
            handleException(resourceBusStopFavorites.exception!!)
          }
        }
      }
    })
  }

  override fun onResume() {
    super.onResume()
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

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.favorites_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.lines_action -> {
        navigateSafe(R.id.actionShowLines)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
