package org.galio.bussantiago.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.koin.android.ext.android.inject

class LauncherFragment : Fragment() {

  private val favoriteDataSource: FavoriteDataSource by inject()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.launcher_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val busStopFavorites = favoriteDataSource.getAll()
    if (busStopFavorites.isEmpty()) {
      navigateSafe(R.id.actionShowLines)
    } else {
      navigateSafe(R.id.actionShowFavorites)
    }
  }
}
