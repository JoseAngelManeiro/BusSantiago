package org.galio.bussantiago.features.stops

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.busstopslist_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.android.viewmodel.ext.android.viewModel

class BusStopsListFragment : Fragment() {

  private val viewModel: BusStopsListViewModel by viewModel()

  companion object {
    private const val LINE_ID_KEY = "line_id_key"
    private const val ROUTE_NAME_KEY = "route_name_key"
    fun newInstance(id: Int, routeName: String): BusStopsListFragment {
      val fragment = BusStopsListFragment()
      val bundle = Bundle()
      bundle.putInt(LINE_ID_KEY, id)
      bundle.putString(ROUTE_NAME_KEY, routeName)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.busstopslist_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val lineId = arguments?.get(LINE_ID_KEY) as Int
    val routeName = arguments?.get(ROUTE_NAME_KEY) as String

    viewModel.busStopModels.observe(viewLifecycleOwner, Observer {
      it?.let { resourceIncidencesModel ->
        when (resourceIncidencesModel.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            busStopsRecyclerView.adapter =
              BusStopsListAdapter(resourceIncidencesModel.data!!) { onBusStopClick(it) }
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
    navigateSafe(R.id.actionShowTimesFragment, TimesFragment.createArguments(busStopModel))
  }
}
