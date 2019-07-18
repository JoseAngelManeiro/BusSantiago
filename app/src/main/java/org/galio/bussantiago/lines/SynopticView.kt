package org.galio.bussantiago.lines

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.AppCompatTextView
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
