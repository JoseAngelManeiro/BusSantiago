package org.galio.bussantiago.features.times

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.busstopslist_fragment.progressBar
import kotlinx.android.synthetic.main.times_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class TimesFragment : Fragment() {

  private val viewModel: TimesViewModel by viewModel()
  private lateinit var busStopModel: BusStopModel

  private val reviewsHelper: ReviewsHelper by inject()

  companion object {
    private const val BUS_STOP_KEY = "bus_stop_key"
    fun createArguments(busStopModel: BusStopModel): Bundle {
      val bundle = Bundle()
      bundle.putParcelable(BUS_STOP_KEY, busStopModel)
      return bundle
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.times_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    busStopModel = arguments?.get(BUS_STOP_KEY) as BusStopModel

    initActionBar(
      title = busStopModel.code,
      subTitle = busStopModel.name,
      backEnabled = true
    )

    setUpFavoriteButton()

    viewModel.setArgs(busStopModel.code, busStopModel.name)

    viewModel.lineRemainingTimeModels.observe(viewLifecycleOwner, Observer { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadTimes() }
        },
        onSuccess = { times ->
          progressBar.visibility = View.GONE
          if (times.isEmpty()) {
            noInfoTextView.visibility = View.VISIBLE
          } else {
            timesRecyclerView.adapter = TimesAdapter(times)
            reviewsHelper.launchReviews(requireActivity())
          }
        }
      )
    })

    viewModel.isFavorite.observe(viewLifecycleOwner, Observer { isFavorite ->
      if (isFavorite) {
        favoriteFAB.setImageResource(R.drawable.ic_fab_favorite)
        favoriteFAB.contentDescription = getString(R.string.favorite_stop)
      } else {
        favoriteFAB.setImageResource(R.drawable.ic_fab_favorite_border)
        favoriteFAB.contentDescription = getString(R.string.no_favorite_stop)
      }
    })

    viewModel.loadTimes()
    viewModel.validateBusStop()
  }

  private fun setUpFavoriteButton() {
    favoriteFAB.setOnClickListener {
      viewModel.changeFavoriteState()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.times_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.sync_action -> {
        if (!timesAreLoading()) viewModel.loadTimes()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun timesAreLoading(): Boolean {
    return progressBar.visibility == View.VISIBLE
  }
}
