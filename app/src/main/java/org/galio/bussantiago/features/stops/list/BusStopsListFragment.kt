package org.galio.bussantiago.features.stops.list

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.busstopslist_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.android.viewmodel.ext.android.viewModel

class BusStopsListFragment : Fragment() {

  private val viewModel: BusStopsListViewModel by viewModel()

  companion object {
    private const val BUS_STOPS_ARGS_KEY = "bus_stops_args_key"
    fun newInstance(busStopsArgs: BusStopsArgs): BusStopsListFragment {
      val fragment = BusStopsListFragment()
      val bundle = Bundle()
      bundle.putParcelable(BUS_STOPS_ARGS_KEY, busStopsArgs)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.busstopslist_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val busStopsArgs = arguments?.get(BUS_STOPS_ARGS_KEY) as BusStopsArgs

    viewModel.setArgs(busStopsArgs)

    viewModel.busStopModels.observe(viewLifecycleOwner, Observer { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          hideProgressBarIfNecessary()
          handleException(it) { viewModel.loadBusStops() }
        },
        onSuccess = { busStopModels ->
          hideProgressBarIfNecessary()
          busStopsRecyclerView.adapter =
            BusStopsListAdapter(busStopModels) { onBusStopClick(it) }
        }
      )
    })

    viewModel.loadBusStops()
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }

  private fun onBusStopClick(busStopModel: BusStopModel) {
    navigateSafe(R.id.actionShowTimesFragment, TimesFragment.createArguments(busStopModel))
  }
}
