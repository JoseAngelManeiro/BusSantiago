package org.galio.bussantiago.shared

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

object SystemBarsHelper {

  private const val TAG_STATUS_BAR_SCRIM = "system_status_bar_scrim"
  private const val TAG_NAV_BAR_SCRIM = "system_nav_bar_scrim"

  fun applyEdgeToEdgeWithScrims(
    window: Window,
    contentView: View? = null,
  ) {
    val decorView = window.decorView as? ViewGroup ?: return
    val context = decorView.context

    val insetsController = WindowCompat.getInsetsController(window, decorView)
    insetsController.isAppearanceLightStatusBars = false
    insetsController.isAppearanceLightNavigationBars = false

    val statusBarScrim = decorView.findViewWithTag<View>(TAG_STATUS_BAR_SCRIM)
      ?: View(context).apply {
        tag = TAG_STATUS_BAR_SCRIM
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        decorView.addView(
          this,
          FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0).apply {
            gravity = Gravity.TOP
          }
        )
      }

    val navBarScrim = decorView.findViewWithTag<View>(TAG_NAV_BAR_SCRIM)
      ?: View(context).apply {
        tag = TAG_NAV_BAR_SCRIM
        setBackgroundColor(Color.BLACK)
        decorView.addView(
          this,
          FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0).apply {
            gravity = Gravity.BOTTOM
          }
        )
      }

    ViewCompat.setOnApplyWindowInsetsListener(decorView) { _, insets ->
      val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
      val navBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())

      statusBarScrim.layoutParams = (statusBarScrim.layoutParams as? FrameLayout.LayoutParams
        ?: FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)).apply {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = statusBars.top
        gravity = Gravity.TOP
      }

      val navLayoutParams = navBarScrim.layoutParams as? FrameLayout.LayoutParams
        ?: FrameLayout.LayoutParams(0, 0)
      when {
        navBars.bottom > 0 -> {
          navLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
          navLayoutParams.height = navBars.bottom
          navLayoutParams.gravity = Gravity.BOTTOM
        }
        navBars.right > 0 -> {
          navLayoutParams.width = navBars.right
          navLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
          navLayoutParams.gravity = Gravity.END
        }
        navBars.left > 0 -> {
          navLayoutParams.width = navBars.left
          navLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
          navLayoutParams.gravity = Gravity.START
        }
        else -> {
          navLayoutParams.width = 0
          navLayoutParams.height = 0
        }
      }
      navBarScrim.layoutParams = navLayoutParams
      insets
    }

    contentView?.let { v ->
      ViewCompat.setOnApplyWindowInsetsListener(v) { targetView, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        targetView.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
      }
      ViewCompat.requestApplyInsets(v)
    }

    ViewCompat.requestApplyInsets(decorView)
  }
}
