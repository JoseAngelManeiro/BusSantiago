package org.galio.bussantiago.menu

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.menu_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.koin.android.viewmodel.ext.android.viewModel

class MenuFragment : DialogFragment() {

  private val viewModel: MenuViewModel by viewModel()

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
    return inflater.inflate(R.layout.menu_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.menuModel.observe(this, Observer {
      it?.let { resourceLineDetails ->
        when (resourceLineDetails.status) {
          Status.LOADING -> {
            // Todo Not Implemented
          }
          Status.SUCCESS -> {
            textMenu.text = it.toString()
          }
          Status.ERROR -> {
            // Todo Not Implemented
          }
        }
      }
    })

    val id = arguments?.get(ID_KEY) as Int
    viewModel.loadLineDetails(id)
  }
}
