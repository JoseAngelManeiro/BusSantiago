package org.galio.bussantiago.ui.information

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.text.HtmlCompat
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.information_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.ui.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class InformationFragment : BaseFragment() {

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
            informationTextView.movementMethod = ScrollingMovementMethod()
            informationTextView.text = information
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            Toast.makeText(
              this.context,
              resourceInformationModel.exception!!.message,
              Toast.LENGTH_SHORT
            ).show()
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
