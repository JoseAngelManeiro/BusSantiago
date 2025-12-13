package org.galio.bussantiago.features.incidences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.features.incidences.ui.IncidencesScreenContainer
import org.galio.bussantiago.common.BusSantiagoTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncidencesFragment : Fragment() {

  private val viewModel: IncidencesViewModel by viewModel()

  companion object {
    private const val ID_KEY = "id_key"
    fun createArguments(lineId: Int): Bundle {
      val bundle = Bundle()
      bundle.putInt(ID_KEY, lineId)
      return bundle
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val composeView = ComposeView(requireContext())
    composeView.setContent {
      BusSantiagoTheme {
        IncidencesScreenContainer(
          lineId = arguments?.getInt(ID_KEY) ?: 0, // TODO: Control what happens when argument is null
          viewModel = viewModel
        )
      }
    }
    return composeView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // We need to keep the Activity's ActionBar for compatibility with the rest of xml Fragments
    initActionBar(title = getString(R.string.incidences), backEnabled = true)
  }
}
