package org.galio.bussantiago.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.search_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.hideKeyboard
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

  private val viewModel: SearchViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.search_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initActionBar(title = getString(R.string.search_bus_stop), backEnabled = true)

    setUpCodeEditText()
    setUpSearchButton()

    viewModel.busStopModel.observe(viewLifecycleOwner, Observer {
      it.let { resourceBusStopModel ->
        when (resourceBusStopModel.status) {
          Status.LOADING -> {
            errorTextView.text = ""
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            progressBar.visibility = View.INVISIBLE
            if (resourceBusStopModel.data == null) {
              errorTextView.text = getString(R.string.bus_stop_not_exist)
            } else {
              navigateSafe(R.id.actionShowTimesFromSearch,
                TimesFragment.createArguments(resourceBusStopModel.data.copy()))
            }
          }
          Status.ERROR -> {
            progressBar.visibility = View.INVISIBLE
            handleException(resourceBusStopModel.exception!!) { search() }
          }
        }
      }
    })
  }

  private fun setUpCodeEditText() {
    codeEditText.setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        search()
        true
      } else {
        false
      }
    }
  }

  private fun setUpSearchButton() {
    searchButton.setOnClickListener { search() }
  }

  private fun search() {
    codeEditText.text.let {
      if (it.isNullOrEmpty()) {
        Toast.makeText(
          context,
          getString(R.string.must_type_code),
          Toast.LENGTH_LONG
        ).show()
      } else {
        viewModel.search(it.toString().toInt())
      }
    }
  }

  override fun onDestroyView() {
    hideKeyboard()
    super.onDestroyView()
  }
}
