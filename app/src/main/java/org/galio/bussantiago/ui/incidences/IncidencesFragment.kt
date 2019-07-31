package org.galio.bussantiago.ui.incidences

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.incidences_fragment.*
import kotlinx.android.synthetic.main.information_fragment.progressBar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.koin.android.viewmodel.ext.android.viewModel

class IncidencesFragment : Fragment() {

  private val viewModel: IncidencesViewModel by viewModel()

  companion object {
    private const val ID_KEY = "id_key"
    fun createArguments(id: Int): Bundle {
      val bundle = Bundle()
      bundle.putInt(ID_KEY, id)
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

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initActionBar(R.string.incidences, true)

    viewModel.incidencesModel.observe(this, Observer {
      it?.let { resourceIncidencesModel ->
        when (resourceIncidencesModel.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            incidencesRecyclerView.adapter = IncidencesAdapter(it.data!!)
            val itemDecoration = DividerItemDecoration(
              incidencesRecyclerView.context,
              LinearLayout.VERTICAL
            )
            incidencesRecyclerView.addItemDecoration(itemDecoration)
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceIncidencesModel.exception!!)
          }
        }
      }
    })

    val id = arguments?.get(ID_KEY) as Int
    viewModel.loadIncidences(id)
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }
}
