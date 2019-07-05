package org.galio.bussantiago.lines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.lines_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
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

    viewModel.lines.observe(this, Observer {
      it?.let { resourceLines ->
        when (resourceLines.status) {
          Status.LOADING -> Toast.makeText(this.context, "Init Loading", Toast.LENGTH_SHORT).show()
          Status.SUCCESS -> {
            Toast.makeText(this.context, "Finish Loading", Toast.LENGTH_SHORT).show()
            textViewLines.text = resourceLines.data.toString()
          }
          Status.ERROR -> {
            Toast.makeText(this.context, "Finish Loading", Toast.LENGTH_SHORT).show()
            Toast.makeText(this.context, resourceLines.exception!!.message, Toast.LENGTH_SHORT)
              .show()
          }
        }
      }
    })
  }
}
