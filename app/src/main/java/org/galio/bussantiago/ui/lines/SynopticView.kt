package org.galio.bussantiago.ui.lines

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import org.galio.bussantiago.R

class SynopticView(
  context: Context,
  attrs: AttributeSet?
) : AppCompatTextView(ContextThemeWrapper(context, R.style.Synoptic), attrs) {

  fun render(synopticModel: SynopticModel) {
    val synopticBackground = this.background as GradientDrawable
    synopticBackground.setColor(Color.parseColor(synopticModel.style))
    this.text = synopticModel.synoptic.removePrefix("L")
  }
}
