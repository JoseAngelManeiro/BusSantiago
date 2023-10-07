package org.galio.bussantiago.features.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.information_fragment.informationTextView
import kotlinx.android.synthetic.main.information_fragment.progressBar
import org.galio.bussantiago.R
import org.galio.bussantiago.common.fromHtml
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment : Fragment() {

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
  ): View? {
    return inflater.inflate(R.layout.information_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.information), backEnabled = true)

    val lineId = arguments?.get(ID_KEY) as Int
    viewModel.setArgs(lineId)

    viewModel.information.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadLineInformation() }
        },
        onSuccess = {
          progressBar.visibility = View.GONE
          informationTextView.text = it.fromHtml()
        }
      )
    }

    viewModel.loadLineInformation()
  }
}
