package org.galio.bussantiago.features.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.databinding.MenuFragmentBinding
import org.galio.bussantiago.features.incidences.IncidencesFragment
import org.galio.bussantiago.features.information.InformationFragment
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.stops.BusStopsContainerFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : DialogFragment() {

  private var _binding: MenuFragmentBinding? = null
  private val binding get() = _binding!!

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
  ): View {
    _binding = MenuFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    lineId = arguments?.getInt(ID_KEY) ?: 0
    viewModel.setArgs(lineId)

    viewModel.menuModel.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
          binding.progressBar.visibility = View.GONE
          handleException(
            it,
            cancel = { dismiss() },
            retry = { viewModel.loadLineDetails() }
          )
        },
        onSuccess = {
          binding.progressBar.visibility = View.GONE
          setUpRecyclerView(it.options)
        }
      )
    }

    viewModel.loadLineDetails()
  }

  private fun setUpRecyclerView(menuOptionModels: List<MenuOptionModel>) {
    binding.menuOptionsRecyclerView.visibility = View.VISIBLE
    binding.menuOptionsRecyclerView.adapter = MenuAdapter(menuOptionModels) {
      onMenuOptionClicked(it)
    }
  }

  private fun onMenuOptionClicked(menuOptionModel: MenuOptionModel) {
    when (menuOptionModel.menuType) {
      MenuType.OUTWARD_ROUTE, MenuType.RETURN_ROUTE -> {
        navigateSafe(
          R.id.actionShowBusStops,
          BusStopsContainerFragment.createArguments(
            BusStopsArgs(lineId = lineId, routeName = menuOptionModel.title!!)
          )
        )
      }

      MenuType.INFORMATION -> {
        navigateSafe(R.id.actionShowInformation, InformationFragment.createArguments(lineId))
      }

      MenuType.INCIDENCES -> {
        navigateSafe(R.id.actionShowIncidences, IncidencesFragment.createArguments(lineId))
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
