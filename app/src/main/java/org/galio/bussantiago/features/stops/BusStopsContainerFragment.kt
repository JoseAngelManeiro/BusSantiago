package org.galio.bussantiago.features.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.galio.bussantiago.common.getArgument
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.databinding.BusstopscontainerFragmentBinding

class BusStopsContainerFragment : Fragment() {

  private var _binding: BusstopscontainerFragmentBinding? = null
  private val binding get() = _binding!!

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
  ): View {
    _binding = BusstopscontainerFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    getArgument<BusStopsArgs>(BUS_STOPS_ARGS_KEY)?.let { busStopsArgs ->
      initActionBar(title = busStopsArgs.routeName, backEnabled = true)

      binding.stopsViewPager.adapter = BusStopsPagerAdapter(
        busStopsArgs,
        requireContext(),
        childFragmentManager
      )

      binding.stopsTabLayout.setupWithViewPager(binding.stopsViewPager)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
