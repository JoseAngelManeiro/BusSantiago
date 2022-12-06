package org.galio.bussantiago.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.settings_fragment.view.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.framework.SettingsPreferences
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

  private val settingsPreferences: SettingsPreferences by inject()
  private val hashMap: HashMap<Int, Int> = hashMapOf()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.settings_fragment, container, false).apply {
      setUpRadioGroup(this)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.settings), backEnabled = true)
  }

  private fun setUpRadioGroup(view: View) {
    hashMap[R.id.favorites_radio_button] = SettingsPreferences.FAVORITES_HOME_VALUE
    hashMap[R.id.lines_radio_button] = SettingsPreferences.LINES_HOME_VALUE

    view.home_screen_radio_group.setOnCheckedChangeListener { radioGroup, _ ->
      val checkedId = radioGroup.checkedRadioButtonId
      hashMap[checkedId]?.let { settingsPreferences.saveHomeScreenType(it) }
    }

    // Init with option saved
    getInitRadioButtonId()?.let { view.home_screen_radio_group.check(it) }
  }

  private fun getInitRadioButtonId(): Int? {
    hashMap.forEach {
      if (it.value == settingsPreferences.getHomeScreenType()) {
        return it.key
      }
    }
    return null
  }
}
