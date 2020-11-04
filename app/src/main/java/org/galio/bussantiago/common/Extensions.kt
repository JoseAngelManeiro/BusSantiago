package org.galio.bussantiago.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat

fun ViewGroup.inflate(itemHolder: Int): View =
  LayoutInflater.from(context).inflate(itemHolder, this, false)

fun String.fromHtml() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
