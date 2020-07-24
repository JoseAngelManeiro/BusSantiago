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

    viewModel.busStopModel.observe(viewLifecycleOwner, Observer { resource ->
      resource.fold(
        onLoading = {
          errorTextView.text = ""
          progressBar.visibility = View.VISIBLE
        },
        onError = {
          progressBar.visibility = View.INVISIBLE
          handleException(it) { search() }
        },
        onSuccess = { busStopModel ->
          progressBar.visibility = View.INVISIBLE
          if (busStopModel == null) {
            errorTextView.text = getString(R.string.bus_stop_not_exist)
          } else {
            navigateSafe(R.id.actionShowTimesFromSearch,
              TimesFragment.createArguments(busStopModel.copy()))
          }
        }
      )
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
