package org.galio.bussantiago.features.lines

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lines_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.BaseHomeFragment
import org.galio.bussantiago.features.menu.MenuFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LinesFragment : BaseHomeFragment() {

  private val viewModel: LinesViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.lines_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initActionBar(title = getString(R.string.lines))

    viewModel.lineModels.observe(viewLifecycleOwner, Observer {
      it?.let { resourceLines ->
        when (resourceLines.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            setUpRecyclerView(resourceLines.data!!)
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceLines.exception!!) { viewModel.loadLines() }
          }
        }
      }
    })

    viewModel.loadLines()
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }

  private fun setUpRecyclerView(lines: List<LineModel>) {
    linesRecyclerView.adapter = LinesAdapter(lines) { onLineClicked(it) }
  }

  private fun onLineClicked(id: Int) {
    navigateSafe(R.id.actionShowMenu, MenuFragment.createArguments(id))
  }
}
