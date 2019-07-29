package org.galio.bussantiago.ui.lines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.lines_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.ui.BaseFragment
import org.galio.bussantiago.ui.menu.MenuFragment
import org.koin.android.viewmodel.ext.android.viewModel

class LinesFragment : BaseFragment() {

  private val viewModel: LinesViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.lines_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initActionBar(R.string.lines)

    viewModel.lines.observe(this, Observer {
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
            Toast.makeText(this.context, resourceLines.exception!!.message, Toast.LENGTH_SHORT)
              .show()
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
    findNavController().navigate(R.id.actionShowMenu, MenuFragment.createArguments(id))
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    inflater?.inflate(R.menu.lines_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      R.id.favorites_action -> {
        Toast.makeText(this.context, "Favoritas", Toast.LENGTH_SHORT).show()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
