package org.galio.bussantiago.common

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun HtmlText(
  html: String,
  modifier: Modifier = Modifier,
  fontSizeSp: Int = 18
) {
  AndroidView(
    modifier = modifier,
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
            return true
          }
        }
        settings.apply {
          javaScriptEnabled = false
          setSupportZoom(true)
          builtInZoomControls = true
          displayZoomControls = false
        }
        setBackgroundColor(0)
      }
    },
    update = { webView ->
      webView.loadDataWithBaseURL(null, wrapInHtml(html, fontSizeSp), "text/html", "utf-8", null)
    }
  )
}

private fun wrapInHtml(content: String, fontSize: Int): String {
  return """
        <html>
        <head>
            <style type="text/css">
                body {
                    font-family: sans-serif;
                    font-size: ${fontSize}px;
                    line-height: 1.5;
                    color: #333333;
                    padding: 0px;
                    margin: 0px;
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
