package org.galio.bussantiago.features.stops.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.getArgument
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.databinding.BusstopslistFragmentBinding
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.times.TimesDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusStopsListFragment : Fragment() {

  private var _binding: BusstopslistFragmentBinding? = null
  private val binding get() = _binding!!

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
  ): View {
    _binding = BusstopslistFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    getArgument<BusStopsArgs>(BUS_STOPS_ARGS_KEY)?.let { busStopsArgs ->
      viewModel.setArgs(busStopsArgs)

      viewModel.busStopModels.observe(viewLifecycleOwner) { resource ->
        resource.fold(
          onLoading = {
            binding.progressBar.visibility = View.VISIBLE
          },
          onError = {
            hideProgressBarIfNecessary()
            handleException(it) { viewModel.loadBusStops() }
          },
          onSuccess = { busStopModels ->
            hideProgressBarIfNecessary()
            binding.busStopsRecyclerView.adapter =
              BusStopsListAdapter(busStopModels) { onBusStopClick(it) }
          }
        )
      }

      viewModel.loadBusStops()
    } ?: Log.w("BusStopsListFragment", "Argument BusStopsArgs was not sent correctly.")
  }

  private fun hideProgressBarIfNecessary() {
    if (binding.progressBar.visibility == View.VISIBLE) {
      binding.progressBar.visibility = View.GONE
    }
  }

  private fun onBusStopClick(busStopModel: BusStopModel) {
    navigateSafe(R.id.actionShowTimes, TimesDialogFragment.createArguments(busStopModel))
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
