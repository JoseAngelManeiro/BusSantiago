package org.galio.bussantiago.features.stops.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.galio.bussantiago.common.getParcelableArgument
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.databinding.BusstopslistFragmentBinding
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusStopsListFragment : Fragment() {

  private var _binding: BusstopslistFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: BusStopsListViewModel by viewModel()
  private val navigator: Navigator by lazy { Navigator(this) }

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
  ): View {
    _binding = BusstopslistFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    getParcelableArgument<BusStopsArgs>(BUS_STOPS_ARGS_KEY)?.let { busStopsArgs ->
      viewModel.busStopModels.observe(viewLifecycleOwner) { resource ->
        resource.fold(
          onLoading = {
            binding.progressBar.visibility = View.VISIBLE
          },
          onError = {
            hideProgressBarIfNecessary()
            handleException(it) { viewModel.loadBusStops(busStopsArgs) }
          },
          onSuccess = { busStopModels ->
            hideProgressBarIfNecessary()
            binding.busStopsRecyclerView.adapter =
              BusStopsListAdapter(busStopModels) { viewModel.onBusStopClick(it) }
          }
        )
      }

      viewModel.navigationEvent.observe(viewLifecycleOwner) { navScreen ->
        navigator.navigate(navScreen)
      }

      viewModel.loadBusStops(busStopsArgs)
    } ?: Log.w("BusStopsListFragment", "Argument BusStopsArgs was not sent correctly.")
  }

  private fun hideProgressBarIfNecessary() {
    if (binding.progressBar.isVisible) {
      binding.progressBar.visibility = View.GONE
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
