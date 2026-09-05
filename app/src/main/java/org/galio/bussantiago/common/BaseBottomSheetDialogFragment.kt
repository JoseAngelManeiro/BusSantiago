package org.galio.bussantiago.common

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.R as androidR
import com.google.android.material.R as materialR

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

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
}
