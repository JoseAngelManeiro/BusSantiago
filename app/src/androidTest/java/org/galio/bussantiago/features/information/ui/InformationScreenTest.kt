package org.galio.bussantiago.features.information.ui

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.galio.bussantiago.common.BusSantiagoTheme
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.features.information.InformationUserInteractions
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InformationScreenTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  private val userInteractions = object : InformationUserInteractions {
    override fun onRetry() {}
    override fun onCancel() {}
  }

  @Before
  fun setUp() {
    Intents.init()
  }

  @After
  fun tearDown() {
    Intents.release()
  }

  @Test
  fun loadingState_showsIndicator() {
    composeTestRule.setContent {
      BusSantiagoTheme {
        InformationScreenContainer(
          informationState = Resource.loading(),
          userInteractions = userInteractions
        )
      }
    }

    composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
  }

  @Test
  fun successState_showsInformation() {
    val information = "Línea 1 info"
    composeTestRule.setContent {
      BusSantiagoTheme {
        InformationScreenContainer(
          informationState = Resource.success(information),
          userInteractions = userInteractions
        )
      }
    }

    composeTestRule.onNodeWithText(information, substring = true).assertIsDisplayed()
  }

  @Test
  fun successState_withLink_opensIntentOnNavigate() {
    val url = "https://example.com/"
    val information = "<a href=\"$url\">Click here</a>"
    composeTestRule.setContent {
      BusSantiagoTheme {
        InformationScreenContainer(
          informationState = Resource.success(information),
          userInteractions = userInteractions
        )
      }
    }

    // Click the text that contains the link
    composeTestRule.onNodeWithTag("InformationText").performClick()

    intended(
      allOf(
        hasAction(Intent.ACTION_VIEW),
        hasData(url)
      )
    )
  }

  @Test
  fun errorState_showsErrorDialog() {
    val errorMessage = "Error message"
    composeTestRule.setContent {
      BusSantiagoTheme {
        InformationScreenContainer(
          informationState = Resource.error(Exception(errorMessage)),
          userInteractions = userInteractions
        )
      }
    }

    composeTestRule.onNodeWithText(errorMessage, substring = true).assertIsDisplayed()
  }
}
