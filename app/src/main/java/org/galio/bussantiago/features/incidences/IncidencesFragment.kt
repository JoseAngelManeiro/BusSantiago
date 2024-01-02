package org.galio.bussantiago.features.incidences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.incidences_fragment.incidencesRecyclerView
import kotlinx.android.synthetic.main.information_fragment.progressBar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
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
  ): View? {
    return inflater.inflate(R.layout.incidences_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.incidences), backEnabled = true)

    val lineId = arguments?.get(ID_KEY) as Int
    viewModel.setArgs(lineId)

    viewModel.incidences.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadIncidences() }
        },
        onSuccess = {
          progressBar.visibility = View.GONE
          incidencesRecyclerView.adapter = IncidencesAdapter(it)
          val itemDecoration = DividerItemDecoration(
            incidencesRecyclerView.context,
            LinearLayout.VERTICAL
          )
          incidencesRecyclerView.addItemDecoration(itemDecoration)
        }
      )
    }

    viewModel.loadIncidences()
  }
}
