package org.galio.bussantiago.features.information.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import org.galio.bussantiago.common.BusSantiagoTheme
import org.galio.bussantiago.shared.R as sharedR

import androidx.compose.ui.platform.testTag

@Composable
fun InformationScreen(information: String) {
  val defaultPadding = dimensionResource(id = sharedR.dimen.default_padding)
  val defaultTextSize = with(LocalDensity.current) {
    dimensionResource(id = sharedR.dimen.default_text_size).toSp()
  }

  SelectionContainer {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(defaultPadding)
    ) {
      Text(
        text = AnnotatedString.fromHtml(
          htmlString = information,
          linkStyles = TextLinkStyles(
            style = SpanStyle(color = MaterialTheme.colors.secondary)
          )
        ),
        fontSize = defaultTextSize,
        modifier = Modifier.testTag("InformationText")
      )
    }
  }
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
