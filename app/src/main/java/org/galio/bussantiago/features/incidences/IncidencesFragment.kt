package org.galio.bussantiago.features.incidences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.galio.bussantiago.R
import org.galio.bussantiago.common.BusSantiagoTheme
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.features.incidences.ui.IncidencesScreenContainer
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IncidencesFragment : Fragment() {

  private val args: IncidencesFragmentArgs by navArgs()
  private val viewModel: IncidencesViewModel by viewModel { parametersOf(args.lineId) }
  private val navigator: Navigator by lazy { Navigator(this) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setContent {
        BusSantiagoTheme {
          IncidencesScreenContainer(
            incidencesState = viewModel.incidences.observeAsState().value!!,
            userInteractions = viewModel
          )
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // We need to keep the Activity's ActionBar for compatibility with the rest of xml Fragments
    initActionBar(title = getString(R.string.incidences), backEnabled = true)

    viewModel.navigationEvent.observe(viewLifecycleOwner) { navScreen ->
      navigator.navigate(navScreen)
    }
  }
}
