package org.galio.bussantiago.features.menu

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.menu_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.incidences.IncidencesFragment
import org.galio.bussantiago.features.information.InformationFragment
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.stops.BusStopsContainerFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    lineId = arguments?.get(ID_KEY) as Int
    viewModel.setArgs(lineId)

    viewModel.menuModel.observe(viewLifecycleOwner, Observer { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          progressBar.visibility = View.GONE
          handleException(
            it,
            cancel = { dismiss() },
            retry = { viewModel.loadLineDetails() }
          )
        },
        onSuccess = {
          progressBar.visibility = View.GONE
          setUpRecyclerView(it.options)
        }
      )
    })

    viewModel.loadLineDetails()
  }

  private fun setUpRecyclerView(menuOptionModels: List<MenuOptionModel>) {
    menuOptionsRecyclerView.visibility = View.VISIBLE
    menuOptionsRecyclerView.adapter = MenuAdapter(menuOptionModels) { onMenuOptionClicked(it) }
  }

  private fun onMenuOptionClicked(menuOptionModel: MenuOptionModel) {
    when (menuOptionModel.menuType) {
      MenuType.OUTWARD_ROUTE, MenuType.RETURN_ROUTE -> {
        navigateSafe(R.id.actionShowBusStops,
          BusStopsContainerFragment.createArguments(
            BusStopsArgs(lineId = lineId, routeName = menuOptionModel.title!!)))
      }
      MenuType.INFORMATION -> {
        navigateSafe(R.id.actionShowInformation, InformationFragment.createArguments(lineId))
      }
      MenuType.INCIDENCES -> {
        navigateSafe(R.id.actionShowIncidences, IncidencesFragment.createArguments(lineId))
      }
    }
  }
}
