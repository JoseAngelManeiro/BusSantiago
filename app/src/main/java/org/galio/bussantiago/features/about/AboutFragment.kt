package org.galio.bussantiago.features.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.galio.bussantiago.R
import org.galio.bussantiago.databinding.AboutFragmentBinding
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
import org.koin.android.ext.android.inject

class AboutFragment : DialogFragment() {

  private var _binding: AboutFragmentBinding? = null
  private val binding get() = _binding!!
  private val analyticsTracker: AnalyticsTracker by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
  }

  override fun onStart() {
    super.onStart()
    dialog?.let { dialog ->
      val width = ViewGroup.LayoutParams.MATCH_PARENT
      val height = ViewGroup.LayoutParams.MATCH_PARENT
      dialog.window?.setLayout(width, height)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = AboutFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpToolbar()
    analyticsTracker.trackScreen(Screens.ABOUT)
  }

  private fun setUpToolbar() {
    with(binding) {
      toolbar.title = getString(R.string.about)
      toolbar.setNavigationIcon(R.drawable.ic_back_button)
      toolbar.setNavigationOnClickListener { dismiss() }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
