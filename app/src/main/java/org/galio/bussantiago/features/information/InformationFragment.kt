package org.galio.bussantiago.features.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.fromHtml
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.databinding.InformationFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment : Fragment() {

  private var _binding: InformationFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: InformationViewModel by viewModel()

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
  ): View {
    _binding = InformationFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.information), backEnabled = true)

    val lineId = arguments?.getInt(ID_KEY) ?: 0
    viewModel.setArgs(lineId)

    viewModel.information.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
          binding.progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadLineInformation() }
        },
        onSuccess = {
          binding.progressBar.visibility = View.GONE
          binding.informationTextView.text = it.fromHtml()
        }
      )
    }

    viewModel.loadLineInformation()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
