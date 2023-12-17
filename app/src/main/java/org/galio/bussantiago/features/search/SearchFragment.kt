package org.galio.bussantiago.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.search_fragment.closeButtonImageView
import kotlinx.android.synthetic.main.search_fragment.progressBar
import kotlinx.android.synthetic.main.search_fragment.searchAutocompleteTextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.hideKeyboard
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.common.showKeyboard
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

  private val viewModel: SearchViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.search_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.search_bus_stop), backEnabled = true)

    viewModel.busStops.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
          searchAutocompleteTextView.isEnabled = false
          closeButtonImageView.visibility = View.GONE
        },
        onError = {
          progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadBusStops() }
        },
        onSuccess = { busStops ->
          progressBar.visibility = View.GONE
          setUpAutocompleteTextView(busStops)
          setUpCloseButtonImageView()
        }
      )
    }

    viewModel.loadBusStops()
  }

  private fun setUpAutocompleteTextView(busStops: List<BusStopSearch>) {
    val adapter = BusStopSearchAdapter(context = requireContext(), busStops = busStops)
    searchAutocompleteTextView.setAdapter(adapter)
    searchAutocompleteTextView.setOnItemClickListener { _, _, _, _ ->
      hideKeyboard()
      searchAutocompleteTextView.clearFocus()
    }
    searchAutocompleteTextView.isEnabled = true
  }

  private fun setUpCloseButtonImageView() {
    closeButtonImageView.visibility = View.VISIBLE
    closeButtonImageView.setOnClickListener {
      searchAutocompleteTextView.setText("")
      searchAutocompleteTextView.showKeyboard()
    }
  }

  // TODO: Invoke when bubble map marker is clicked
  private fun navigateToTimesScreen(busStopModel: BusStopModel) {
    navigateSafe(
      R.id.actionShowTimesFromSearch,
      TimesFragment.createArguments(busStopModel.copy())
    )
  }

  override fun onDestroyView() {
    hideKeyboard()
    super.onDestroyView()
  }
}
