package org.galio.bussantiago.features.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.databinding.MenuFragmentBinding
import org.galio.bussantiago.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : DialogFragment() {

  private var _binding: MenuFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: MenuViewModel by viewModel()
  private val navigator: Navigator by lazy { Navigator(this) }
  private val menuTextUtils: MenuTextUtils by inject()

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

    arguments?.getInt(ID_KEY)?.let { lineId ->
      viewModel.menuModel.observe(viewLifecycleOwner) { resource ->
        resource.fold(
          onLoading = {
            binding.progressBar.visibility = View.VISIBLE
          },
          onError = { exception ->
            binding.progressBar.visibility = View.GONE
            handleException(
              exception = exception,
              cancel = { dismiss() },
              retry = { viewModel.loadLineDetails(lineId) }
            )
          },
          onSuccess = { menuModel ->
            binding.progressBar.visibility = View.GONE
            setUpView(menuModel, lineId)
          }
        )
      }

      viewModel.navigationEvent.observe(viewLifecycleOwner) { navScreen ->
        navigator.navigate(navScreen)
      }

      viewModel.loadLineDetails(lineId)
    } ?: Log.w("MenuFragment", "Argument line id was not sent correctly.")
  }

  private fun setUpView(menuModel: MenuModel, lineId: Int) {
    with(binding) {
      menuOptionsContainer.visibility = View.VISIBLE
      menuOptionsTextView.text =
        getString(R.string.line_name, menuModel.synopticModel.getSynopticFormatted())
      menuOptionsRecyclerView.adapter =
        MenuAdapter(items = menuModel.options, menuTextUtils = menuTextUtils) {
          viewModel.onMenuOptionClicked(it, lineId)
        }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
