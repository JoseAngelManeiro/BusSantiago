package org.galio.bussantiago.lines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lines_fragment.*
import org.galio.bussantiago.R
import org.koin.android.viewmodel.ext.android.viewModel

class LinesFragment : Fragment() {

  private val viewModel: LinesViewModel by viewModel()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.lines_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.getLiveString().observe(this, Observer {
      textViewLines.text = it
    })
  }
}
