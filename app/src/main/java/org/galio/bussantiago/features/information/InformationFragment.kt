package org.galio.bussantiago.features.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.galio.bussantiago.R
import org.galio.bussantiago.common.BusSantiagoTheme
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.features.information.ui.InformationScreenContainer
import org.galio.bussantiago.navigation.Navigator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InformationFragment : Fragment() {

  private val args: InformationFragmentArgs by navArgs()
  private val viewModel: InformationViewModel by viewModel { parametersOf(args.lineId) }
  private val navigator: Navigator by lazy { Navigator(this) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        BusSantiagoTheme {
          val informationState by viewModel.information.observeAsState(Resource.loading())
          InformationScreenContainer(
            informationState = informationState,
            userInteractions = viewModel
          )
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.information), backEnabled = true)

    viewModel.navigationEvent.observe(viewLifecycleOwner) { navScreen ->
      navigator.navigate(navScreen)
    }
  }
}
