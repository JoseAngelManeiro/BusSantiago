package org.galio.bussantiago

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.galio.bussantiago.features.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SampleInstrumentedTest {

  @get:Rule
  val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
    android.Manifest.permission.ACCESS_FINE_LOCATION,
    android.Manifest.permission.ACCESS_NETWORK_STATE
  )

  @Test
  fun verifyActivityIsLaunchedWithTitle() {
    ActivityScenario.launch(MainActivity::class.java)

    // Check that the activity is displayed with correct title
    onView(withText("Bus Santiago")).check(matches(isDisplayed()))
  }
}
