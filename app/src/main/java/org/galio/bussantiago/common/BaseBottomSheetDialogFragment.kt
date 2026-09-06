package org.galio.bussantiago.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.galio.bussantiago.databinding.BottomSheetWrapperBinding
import android.R as androidR
import com.google.android.material.R as materialR

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

  private var _wrapperBinding: BottomSheetWrapperBinding? = null

  abstract fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup): View

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _wrapperBinding = BottomSheetWrapperBinding.inflate(inflater, container, false)
    onCreateContentView(inflater, _wrapperBinding!!.bottomSheetContentContainer)
    return _wrapperBinding!!.root
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return super.onCreateDialog(savedInstanceState).apply {
      // Set the opacity level of the parent shadow behind dialog (1.0f is completely black)
      window?.setDimAmount(0.3f)

      setOnShowListener {
        // Set transparency to dialog layer
        val bottomSheet = findViewById<View>(materialR.id.design_bottom_sheet) as FrameLayout
        bottomSheet.setBackgroundResource(androidR.color.transparent)
        // Expand the dialog to show all the content
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _wrapperBinding = null
  }
}
