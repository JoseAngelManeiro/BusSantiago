package org.galio.bussantiago.features.incidences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.galio.bussantiago.R
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.features.incidences.ui.IncidencesScreenContainer
import org.galio.bussantiago.common.BusSantiagoTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncidencesFragment : Fragment() {

  private val viewModel: IncidencesViewModel by viewModel()
  private val args: IncidencesFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setContent {
        BusSantiagoTheme {
          IncidencesScreenContainer(
            lineId = args.lineId,
            viewModel = viewModel
          )
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // We need to keep the Activity's ActionBar for compatibility with the rest of xml Fragments
    initActionBar(title = getString(R.string.incidences), backEnabled = true)
  }
}
