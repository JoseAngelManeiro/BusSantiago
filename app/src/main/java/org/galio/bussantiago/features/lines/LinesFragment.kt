package org.galio.bussantiago.features.lines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.lines_fragment.linesRecyclerView
import kotlinx.android.synthetic.main.lines_fragment.progressBar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.menu.MenuFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LinesFragment : Fragment() {

  private val viewModel: LinesViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.lines_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.lines), backEnabled = true)

    viewModel.lineModels.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          hideProgressBarIfNecessary()
          handleException(it) { viewModel.loadLines() }
        },
        onSuccess = {
          hideProgressBarIfNecessary()
          setUpRecyclerView(it)
        }
      )
    }

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
