package org.galio.bussantiago.features.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.busstopscontainer_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.initActionBar

class BusStopsContainerFragment : Fragment() {

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
    return inflater.inflate(R.layout.busstopscontainer_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val lineId = arguments?.get(LINE_ID_KEY) as Int
    val routeName = arguments?.get(ROUTE_NAME_KEY) as String

    initActionBar(title = routeName, backEnabled = true)

    stops_viewPager.adapter = BusStopsPagerAdapter(
      lineId, routeName, context!!, childFragmentManager)
    stops_tabLayout.setupWithViewPager(stops_viewPager)
  }
}
