package org.galio.bussantiago.common

import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
  append(this@toAnnotatedString.toString())
  getSpans(0, length, Any::class.java).forEach { span ->
    val start = getSpanStart(span)
    val end = getSpanEnd(span)
    if (start < 0 || end < 0 || start > length || end > length) return@forEach

    when (span) {
      is StyleSpan -> when (span.style) {
        android.graphics.Typeface.BOLD -> addStyle(
          SpanStyle(fontWeight = FontWeight.Bold),
          start,
          end
        )

        android.graphics.Typeface.ITALIC -> addStyle(
          SpanStyle(fontStyle = FontStyle.Italic),
          start,
          end
        )

        android.graphics.Typeface.BOLD_ITALIC -> addStyle(
          SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
          start,
          end
        )
      }

      is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
      is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
      is URLSpan -> {
        addLink(
          url = LinkAnnotation.Url(
            url = span.url,
            styles = TextLinkStyles(
              style = SpanStyle(
                color = Color(0xFF007BFF), // Standard link blue
                textDecoration = TextDecoration.Underline
              )
            )
          ),
          start = start,
          end = end
        )
      }
    }
  }
}
