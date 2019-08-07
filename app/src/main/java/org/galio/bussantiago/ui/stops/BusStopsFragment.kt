package org.galio.bussantiago.ui.stops

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.busstops_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.initActionBar
import org.koin.android.viewmodel.ext.android.viewModel

class BusStopsFragment : Fragment() {

  private val viewModel: BusStopsViewModel by viewModel()

  companion object {
    private const val LINE_ID_KEY = "line_id_key"
    private const val ROUTE_NAME_KEY = "route_name_key"
    fun createArguments(id: Int, routeName: String): Bundle {
      val bundle = Bundle()
      bundle.putInt(LINE_ID_KEY, id)
      bundle.putString(ROUTE_NAME_KEY, routeName)
      return bundle
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.busstops_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val lineId = arguments?.get(LINE_ID_KEY) as Int
    val routeName = arguments?.get(ROUTE_NAME_KEY) as String

    initActionBar(title = routeName, backEnabled = true)

    viewModel.busStopModels.observe(this, Observer {
      it?.let { resourceIncidencesModel ->
        when (resourceIncidencesModel.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            busStopsRecyclerView.adapter =
              BusStopsAdapter(resourceIncidencesModel.data!!) { onBusStopClick(it) }
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceIncidencesModel.exception!!)
          }
        }
      }
    })

    viewModel.loadBusStops(lineId, routeName)
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }

  private fun onBusStopClick(busStopModel: BusStopModel) {
    // Todo not implemented
    Toast.makeText(context, busStopModel.code, Toast.LENGTH_SHORT).show()
  }
}
