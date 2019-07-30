package org.galio.bussantiago.ui.information

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.core.text.HtmlCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.information_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.koin.android.viewmodel.ext.android.viewModel

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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.information_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initActionBar(R.string.information, true)

    viewModel.informationModel.observe(this, Observer {
      it?.let { resourceInformationModel ->
        when (resourceInformationModel.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            val information = HtmlCompat.fromHtml(
              resourceInformationModel.data!!,
              HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            informationTextView.text = information
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceInformationModel.exception!!)
          }
        }
      }
    })

    val id = arguments?.get(ID_KEY) as Int
    viewModel.loadLineInformation(id)
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }
}
