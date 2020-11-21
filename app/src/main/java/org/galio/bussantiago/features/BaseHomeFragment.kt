package org.galio.bussantiago.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.lines.LinesFragment

open class BaseHomeFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    val menuItem = menu.findItem(R.id.nav_action)
    if (this is LinesFragment) {
      menuItem.setTitle(R.string.favorites)
      menuItem.setIcon(R.drawable.ic_menu_favorite)
    } else {
      menuItem.setTitle(R.string.lines)
      menuItem.setIcon(R.drawable.ic_menu_lines)
    }
    super.onPrepareOptionsMenu(menu)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.home_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.nav_action -> {
        if (this is LinesFragment) {
          navigateSafe(R.id.actionShowFavorites)
        } else {
          navigateSafe(R.id.actionShowLines)
        }
        true
      }
      R.id.search_action -> {
        navigateSafe(R.id.actionShowSearchBusStop)
        true
      }
      R.id.settings_action -> {
        Toast.makeText(context, "Ajustes", Toast.LENGTH_SHORT).show()
        true
      }
      R.id.about_action -> {
        navigateSafe(R.id.actionShowAbout)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
