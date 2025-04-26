package org.galio.bussantiago.features.incidences.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import org.galio.bussantiago.shared.R as sharedR

@Composable
fun IncidencesScreen(incidences: List<String>) {
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(incidences) { incidence ->
      IncidenceItem(incidenceText = incidence)
    }
  }
}

@Composable
fun IncidenceItem(incidenceText: String) {
  SelectionContainer {
    Text(
      text = AnnotatedString.fromHtml(incidenceText),
      fontSize = with(LocalDensity.current) {
        dimensionResource(id = sharedR.dimen.default_text_size).toSp()
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = sharedR.dimen.default_padding))
    )
  }
}
