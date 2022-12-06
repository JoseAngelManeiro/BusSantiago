package org.galio.bussantiago.features.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.busstopscontainer_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.initActionBar

class BusStopsContainerFragment : Fragment() {

  companion object {
    private const val BUS_STOPS_ARGS_KEY = "bus_stops_args_key"
    fun createArguments(busStopsArgs: BusStopsArgs): Bundle {
      val bundle = Bundle()
      bundle.putParcelable(BUS_STOPS_ARGS_KEY, busStopsArgs)
      return bundle
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.busstopscontainer_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val busStopsArgs = arguments?.get(BUS_STOPS_ARGS_KEY) as BusStopsArgs

    initActionBar(title = busStopsArgs.routeName, backEnabled = true)

    stops_viewPager.adapter = BusStopsPagerAdapter(
      busStopsArgs, requireContext(), childFragmentManager
    )
    stops_tabLayout.setupWithViewPager(stops_viewPager)
  }
}
