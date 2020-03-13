package org.galio.bussantiago.features.stops

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.galio.bussantiago.R
import org.galio.bussantiago.features.stops.list.BusStopsListFragment
import org.galio.bussantiago.features.stops.map.BusStopsMapFragment

class BusStopsPagerAdapter(
  private val busStopsArgs: BusStopsArgs,
  private val context: Context,
  fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment {
    return if (position == 0) {
      BusStopsListFragment.newInstance(busStopsArgs)
    } else {
      BusStopsMapFragment.newInstance(busStopsArgs)
    }
  }

  override fun getCount() = 2

  override fun getPageTitle(position: Int): CharSequence? {
    return when (position) {
      0 -> context.getString(R.string.lista)
      1 -> context.getString(R.string.mapa)
      else -> null
    }
  }
}