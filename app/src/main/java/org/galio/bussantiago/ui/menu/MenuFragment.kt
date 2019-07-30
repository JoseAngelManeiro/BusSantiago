package org.galio.bussantiago.ui.menu

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.menu_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.ui.information.InformationFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MenuFragment : DialogFragment() {

  private val viewModel: MenuViewModel by viewModel()
  private var lineId: Int = 0

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
      it?.let { resourceMenuModel ->
        when (resourceMenuModel.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            setUpRecyclerView(resourceMenuModel.data!!.options)
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceMenuModel.exception!!)
            dismiss()
          }
        }
      }
    })

    lineId = arguments?.get(ID_KEY) as Int
    viewModel.loadLineDetails(lineId)
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }

  private fun setUpRecyclerView(menuOptionModels: List<MenuOptionModel>) {
    menuOptionsRecyclerView.visibility = View.VISIBLE
    menuOptionsRecyclerView.adapter = MenuAdapter(menuOptionModels) { onMenuOptionClicked(it) }
  }

  private fun onMenuOptionClicked(menuOptionModel: MenuOptionModel) {
    when (menuOptionModel.menuType) {
      MenuType.OUTWARD_ROUTE, MenuType.RETURN_ROUTE -> {
        // Todo Not implemented
      }
      MenuType.INFORMATION -> {
        findNavController()
          .navigate(R.id.actionShowInformation, InformationFragment.createArguments(lineId))
      }
      MenuType.INCIDENCES -> {
        // Todo Not implemented
      }
    }
  }
}
