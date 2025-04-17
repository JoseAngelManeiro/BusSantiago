package org.galio.bussantiago.common.model

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatTextView
import org.galio.bussantiago.R
import org.galio.bussantiago.shared.SynopticModel
import androidx.core.graphics.toColorInt

class SynopticView(
  context: Context,
  attrs: AttributeSet?
) : AppCompatTextView(ContextThemeWrapper(context, R.style.Synoptic), attrs) {

  fun render(synopticModel: SynopticModel) {
    val synopticBackground = this.background as GradientDrawable
    synopticBackground.setColor(synopticModel.style.toColorInt())
    this.text = synopticModel.getSynopticFormatted()
  }
}
