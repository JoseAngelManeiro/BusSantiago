package org.galio.bussantiago.features.stops

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.galio.bussantiago.R

class BusStopsPagerAdapter(
  private val id: Int,
  private val routeName: String,
  private val context: Context,
  fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getItem(position: Int): Fragment {
    return if (position == 0) {
      BusStopsListFragment.newInstance(id, routeName)
    } else {
      BusStopsMapFragment.newInstance(id, routeName)
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
