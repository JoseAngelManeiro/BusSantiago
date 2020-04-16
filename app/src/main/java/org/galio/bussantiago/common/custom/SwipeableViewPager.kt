package org.galio.bussantiago.common.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import org.galio.bussantiago.R

/*
* ViewPager is swipeable by default.
* This class has the objective of to use the "swipeable" property to disable this feature if necessary.
 */
class SwipeableViewPager : ViewPager {

  private var swipeable = true

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeableViewPager)
    try {
      swipeable = typedArray.getBoolean(R.styleable.SwipeableViewPager_swipeable, true)
    } finally {
      typedArray.recycle()
    }
  }

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    return if (swipeable) super.onInterceptTouchEvent(event) else false
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    return if (swipeable) super.onTouchEvent(event) else false
  }
}
