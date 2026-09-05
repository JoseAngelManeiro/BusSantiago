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
import org.galio.bussantiago.databinding.BottomSheetWrapperBinding
import org.galio.bussantiago.databinding.MapMarkerBottomSheetContentBinding
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator

class MapMarkerDialogFragment : BaseBottomSheetDialogFragment() {

  private var _wrapperBinding: BottomSheetWrapperBinding? = null
  private val wrapperBinding get() = _wrapperBinding!!

  private var _binding: MapMarkerBottomSheetContentBinding? = null
  private val binding get() = _binding!!

  private val args: MapMarkerDialogFragmentArgs by navArgs()
  private val navigator: Navigator by lazy { Navigator(this) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _wrapperBinding = BottomSheetWrapperBinding.inflate(inflater, container, false)
    _binding = MapMarkerBottomSheetContentBinding.inflate(
      inflater,
      wrapperBinding.bottomSheetContentContainer,
      true
    )

    return wrapperBinding.root
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

      binding.linesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 6)
      binding.linesRecyclerView.adapter = LinesBadgeAdapter(busStop.lines)
    }

    binding.seeArrivalsButton.setOnClickListener {
      navigator.navigate(NavScreen.Times(BusStopModel(busStop.code, busStop.name)))
      dismiss()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _wrapperBinding = null
    _binding = null
  }
}
