package org.galio.bussantiago.features.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import org.galio.bussantiago.R
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.databinding.BusstopscontainerFragmentBinding

class BusStopsContainerFragment : Fragment() {

  private var _binding: BusstopscontainerFragmentBinding? = null
  private val binding get() = _binding!!

  private val args: BusStopsContainerFragmentArgs by navArgs()

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

    val busStopsArgs = args.busStopsArgs
    initActionBar(title = busStopsArgs.routeName, backEnabled = true)

    with(binding) {
      stopsViewPager.adapter = BusStopsPagerAdapter(requireActivity(), busStopsArgs)
      stopsViewPager.isUserInputEnabled = false // Disable swipe gestures

      TabLayoutMediator(stopsTabLayout, stopsViewPager) { tab, position ->
        tab.text = when (position) {
          0 -> getString(R.string.map)
          1 -> getString(R.string.list)
          else -> null
        }
      }.attach()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
