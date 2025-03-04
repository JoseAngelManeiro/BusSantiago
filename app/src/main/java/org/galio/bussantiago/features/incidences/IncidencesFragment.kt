package org.galio.bussantiago.features.incidences

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.databinding.IncidencesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncidencesFragment : Fragment() {

  private var _binding: IncidencesFragmentBinding? = null
  private val binding get() = _binding!!

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
    _binding = IncidencesFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.incidences), backEnabled = true)

    arguments?.getInt(ID_KEY)?.let { lineId ->
      setUpObservers(lineId)

      viewModel.loadIncidences(lineId)
    } ?: Log.w("IncidencesFragment", "Argument line id was not sent correctly.")
  }

  private fun setUpObservers(lineId: Int) {
    viewModel.incidences.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
          binding.progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadIncidences(lineId) }
        },
        onSuccess = {
          binding.progressBar.visibility = View.GONE
          binding.incidencesRecyclerView.adapter = IncidencesAdapter(it)
          val itemDecoration = DividerItemDecoration(
            binding.incidencesRecyclerView.context,
            LinearLayout.VERTICAL
          )
          binding.incidencesRecyclerView.addItemDecoration(itemDecoration)
        }
      )
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
