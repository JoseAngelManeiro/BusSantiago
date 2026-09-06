package org.galio.bussantiago.features.mapmarker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import org.galio.bussantiago.R
import org.galio.bussantiago.common.BaseBottomSheetDialogFragment
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.databinding.MapMarkerBottomSheetContentBinding
import org.galio.bussantiago.framework.analytics.AnalyticsEvents
import org.galio.bussantiago.framework.analytics.AnalyticsParams
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator
import org.koin.android.ext.android.inject

class MapMarkerDialogFragment : BaseBottomSheetDialogFragment() {

  companion object {
    private const val LINES_GRID_SPAN_COUNT = 6
  }

  private var _binding: MapMarkerBottomSheetContentBinding? = null
  private val binding get() = _binding!!

  private val args: MapMarkerDialogFragmentArgs by navArgs()
  private val navigator: Navigator by lazy { Navigator(this) }
  private val analyticsTracker: AnalyticsTracker by inject()

  override fun onCreateContentView(
    inflater: LayoutInflater, container: ViewGroup
  ): View {
    _binding = MapMarkerBottomSheetContentBinding.inflate(inflater, container, true)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val busStop = args.busStopModel

    binding.stopNameTextView.text = busStop.name
    binding.stopCodeTextView.text = getString(
      R.string.map_bottom_sheet_stop_code,
      busStop.code
    )

    if (busStop.lines.isEmpty()) {
      binding.linesTitleTextView.visibility = View.GONE
      binding.linesRecyclerView.visibility = View.GONE
    } else {
      binding.linesTitleTextView.visibility = View.VISIBLE
      binding.linesRecyclerView.visibility = View.VISIBLE

      binding.linesRecyclerView.layoutManager = GridLayoutManager(requireContext(), LINES_GRID_SPAN_COUNT)
      binding.linesRecyclerView.adapter = LinesBadgeAdapter(busStop.lines)
    }

    binding.seeArrivalsButton.setOnClickListener {
      analyticsTracker.trackEvent(
        AnalyticsEvents.SELECT_STOP,
        mapOf(
          AnalyticsParams.ORIGIN to Screens.SEARCH,
          AnalyticsParams.STOP_CODE to busStop.code,
          AnalyticsParams.STOP_NAME to busStop.name
        )
      )
      navigator.navigate(NavScreen.Times(BusStopModel(busStop.code, busStop.name)))
      dismiss()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
