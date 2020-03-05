package org.galio.bussantiago.features.stops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R

class BusStopsMapFragment : Fragment() {

  companion object {
    private const val LINE_ID_KEY = "line_id_key"
    private const val ROUTE_NAME_KEY = "route_name_key"
    fun newInstance(id: Int, routeName: String): BusStopsMapFragment {
      val fragment = BusStopsMapFragment()
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
    return inflater.inflate(R.layout.busstopsmap_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val lineId = arguments?.get(LINE_ID_KEY) as Int
    val routeName = arguments?.get(ROUTE_NAME_KEY) as String
  }
}
