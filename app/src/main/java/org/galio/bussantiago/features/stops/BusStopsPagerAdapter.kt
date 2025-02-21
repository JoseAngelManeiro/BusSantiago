package org.galio.bussantiago.features.stops

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.galio.bussantiago.features.stops.list.BusStopsListFragment
import org.galio.bussantiago.features.stops.map.BusStopsMapFragment

class BusStopsPagerAdapter(
  fragmentActivity: FragmentActivity,
  private val busStopsArgs: BusStopsArgs
) : FragmentStateAdapter(fragmentActivity) {

  override fun createFragment(position: Int): Fragment {
    return if (position == 0) {
      BusStopsMapFragment.newInstance(busStopsArgs)
    } else {
      BusStopsListFragment.newInstance(busStopsArgs)
    }
  }

  override fun getItemCount() = 2
}
