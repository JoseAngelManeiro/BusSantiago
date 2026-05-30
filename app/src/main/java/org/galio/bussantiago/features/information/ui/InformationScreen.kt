package org.galio.bussantiago.features.information.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import org.galio.bussantiago.common.BusSantiagoTheme
import org.galio.bussantiago.common.HtmlText
import org.galio.bussantiago.shared.R as sharedR

@Composable
fun InformationScreen(information: String) {
  val defaultPadding = dimensionResource(id = sharedR.dimen.default_padding)

  HtmlText(
    html = information,
    modifier = Modifier
      .fillMaxSize()
      .padding(defaultPadding)
      .testTag("InformationText"),
    fontSizeSp = 18
  )
}

@Preview(showBackground = true)
@Composable
private fun InformationScreenPreview() {
  BusSantiagoTheme {
    InformationScreen(
      information = "<b>Línea 1:</b><br>Información sobre a liña 1.<p><a href=\"https://example.com\">Máis info</a></p>"
    )
  }
}
