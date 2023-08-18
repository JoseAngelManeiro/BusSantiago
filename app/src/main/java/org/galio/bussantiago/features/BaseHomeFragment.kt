package org.galio.bussantiago.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import org.galio.bussantiago.R
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.lines.LinesFragment

open class BaseHomeFragment : Fragment() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val menuHost: MenuHost = requireActivity()

    menuHost.addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuItem = menu.findItem(R.id.nav_action)
        if (this@BaseHomeFragment is LinesFragment) {
          menuItem.setTitle(R.string.favorites)
          menuItem.setIcon(R.drawable.ic_menu_favorite)
        } else {
          menuItem.setTitle(R.string.lines)
          menuItem.setIcon(R.drawable.ic_menu_lines)
        }
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
          R.id.nav_action -> {
            if (this@BaseHomeFragment is LinesFragment) {
              navigateSafe(R.id.actionShowFavorites)
            } else {
              navigateSafe(R.id.actionShowLines)
            }
            return true
          }
          R.id.search_action -> {
            navigateSafe(R.id.actionShowSearchBusStop)
            return true
          }
          R.id.settings_action -> {
            navigateSafe(R.id.actionShowSettings)
            return true
          }
          R.id.about_action -> {
            navigateSafe(R.id.actionShowAbout)
            return true
          }
          else ->
            return false
        }
      }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
  }
}
