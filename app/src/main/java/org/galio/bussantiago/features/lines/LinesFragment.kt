package org.galio.bussantiago.features.lines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.databinding.LinesFragmentBinding
import org.galio.bussantiago.features.menu.MenuFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LinesFragment : Fragment() {

  private var _binding: LinesFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: LinesViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = LinesFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.lines), backEnabled = true)

    viewModel.lineModels.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          updateProgressBarStatus(visible = true)
        },
        onError = { exception ->
          updateProgressBarStatus(visible = false)
          handleException(exception, retry = { viewModel.loadLines() })
        },
        onSuccess = { lines ->
          updateProgressBarStatus(visible = false)
          setUpLinesAdapter(lines)
        }
      )
    }

    viewModel.loadLines()
  }

  private fun updateProgressBarStatus(visible: Boolean) {
    binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
  }

  private fun setUpLinesAdapter(lines: List<LineModel>) {
    binding.linesRecyclerView.adapter = LinesAdapter(lines) { onLineClicked(it) }
  }

  private fun onLineClicked(id: Int) {
    navigateSafe(R.id.actionShowMenu, MenuFragment.createArguments(id))
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
