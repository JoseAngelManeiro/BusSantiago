package org.galio.bussantiago.features.information.ui

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.tooling.preview.Preview
import org.galio.bussantiago.common.BusSantiagoTheme

@Composable
fun InformationScreen(information: String) {
  AndroidView(
    modifier = Modifier
      .fillMaxSize()
      .testTag("InformationText"),
    factory = { context ->
      WebView(context).apply {
        webViewClient = object : WebViewClient() {
          override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
          ): Boolean {
            request?.url?.let { uri ->
              val intent = Intent(Intent.ACTION_VIEW, uri)
              context.startActivity(intent)
            }
            return true // We handled it
          }
        }
        settings.apply {
          javaScriptEnabled = false
          setSupportZoom(true)
          builtInZoomControls = true
          displayZoomControls = false // Pinch-to-zoom works, but buttons are hidden
        }
        setBackgroundColor(0)
      }
    },
    update = { webView ->
      webView.loadDataWithBaseURL(null, wrapInHtml(information), "text/html", "utf-8", null)
    }
  )
}

private fun wrapInHtml(content: String): String {
  return """
        <html>
        <head>
            <style type="text/css">
                body {
                    font-family: sans-serif;
                    font-size: 16px;
                    line-height: 1.5;
                    color: #333333;
                    padding: 8px;
                }
                a {
                    color: #007bff;
                    text-decoration: none;
                }
            </style>
        </head>
        <body>
            $content
        </body>
        </html>
    """.trimIndent()
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
