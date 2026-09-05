package org.galio.bussantiago.features.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.LineUiModel
import org.galio.bussantiago.databinding.BottomSheetWrapperBinding
import org.galio.bussantiago.databinding.MapMarkerBottomSheetContentBinding
import org.galio.bussantiago.databinding.SynopticBadgeItemBinding
import org.galio.bussantiago.shared.SynopticModel
import android.R as androidR
import com.google.android.material.R as materialR

class MapMarkerBottomSheetFragment : BottomSheetDialogFragment() {

    private val args: MapMarkerBottomSheetFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.3f)

            setOnShowListener {
                val bottomSheet = findViewById<View>(materialR.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(androidR.color.transparent)
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private var _wrapperBinding: BottomSheetWrapperBinding? = null
    private val wrapperBinding get() = _wrapperBinding!!

    private var _contentBinding: MapMarkerBottomSheetContentBinding? = null
    private val contentBinding get() = _contentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val busStop = args.busStopModel

        _wrapperBinding = BottomSheetWrapperBinding.inflate(inflater, container, false)
        _contentBinding = MapMarkerBottomSheetContentBinding.inflate(inflater, wrapperBinding.bottomSheetContentContainer, true)

        contentBinding.stopNameTextView.text = busStop.name
        contentBinding.stopCodeTextView.text = getString(R.string.map_bottom_sheet_stop_code, busStop.code)

        if (busStop.lines.isEmpty()) {
            contentBinding.linesTitleTextView.visibility = View.GONE
            contentBinding.linesRecyclerView.visibility = View.GONE
        } else {
            contentBinding.linesTitleTextView.visibility = View.VISIBLE
            contentBinding.linesRecyclerView.visibility = View.VISIBLE
            
            contentBinding.linesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 6)
            contentBinding.linesRecyclerView.adapter = LinesBadgeAdapter(busStop.lines)
        }

        contentBinding.seeArrivalsButton.setOnClickListener {
            setFragmentResult(
                "requestKey_seeArrivals", 
                bundleOf("busStopCode" to busStop.code, "busStopName" to busStop.name)
            )
            dismiss()
        }

        return wrapperBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _wrapperBinding = null
        _contentBinding = null
    }

    private class LinesBadgeAdapter(private val lines: List<LineUiModel>) : RecyclerView.Adapter<LinesBadgeAdapter.ViewHolder>() {
        class ViewHolder(val binding: SynopticBadgeItemBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = SynopticBadgeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val line = lines[position]
            holder.binding.root.render(
                SynopticModel(synoptic = line.synoptic, style = line.style)
            )
        }

        override fun getItemCount() = lines.size
    }
}
