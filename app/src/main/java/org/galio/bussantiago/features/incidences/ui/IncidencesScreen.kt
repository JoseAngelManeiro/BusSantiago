package org.galio.bussantiago.features.incidences.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import org.galio.bussantiago.common.BusSantiagoTheme
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.shared.R as sharedR
import java.util.Date

@Composable
fun IncidencesScreen(incidences: List<Incidence>) {
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    itemsIndexed(incidences) { index, incidence ->
      IncidenceItem(incidence = incidence)
      if (index < incidences.lastIndex) {
        Divider()
      }
    }
  }
}

@Composable
fun IncidenceItem(incidence: Incidence) {
  val defaultPadding = dimensionResource(id = sharedR.dimen.default_padding)
  val titleTextSize = with(LocalDensity.current) {
    dimensionResource(id = sharedR.dimen.large_text_size).toSp()
  }
  val descriptionTextSize = with(LocalDensity.current) {
    dimensionResource(id = sharedR.dimen.default_text_size).toSp()
  }

  SelectionContainer {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(defaultPadding)
    ) {
      Text(
        text = incidence.title,
        fontSize = titleTextSize,
        color = MaterialTheme.colors.primary
      )
      Text(
        text = AnnotatedString.fromHtml(
          htmlString = incidence.description,
          linkStyles = TextLinkStyles(
            style = SpanStyle(color = MaterialTheme.colors.secondary)
          )
        ),
        fontSize = descriptionTextSize,
        modifier = Modifier.padding(top = defaultPadding)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun IncidencesScreenPreview() {
  BusSantiagoTheme {
    IncidencesScreen(
      incidences = listOf(
        Incidence(
          id = 1,
          title = "Información folga 12/12 (10:00)",
          description =
            "Información estado servicios mínimos (huelga 12/12, 10:00):<br>" +
            "*Circulando las líneas 1, 7, C2, C4, P6 e P8 @PazodeRaxoi",
          startDate = Date(),
          endDate = null
        ),
        Incidence(
          id = 2,
          title = "SERVIZOS MÍNIMOS folga 12, 15 e 19 decembro 2025",
          description =
            "<p><b><u>SERVICIOS MÍNIMOS en el transporte urbano</u></b><br>" +
            "Información de servicios mínimos decretados.</p>" +
            "<p><a href=\"https://example.com\">Ver documento adjunto</a></p>",
          startDate = Date(),
          endDate = null
        )
      )
    )
  }
}
