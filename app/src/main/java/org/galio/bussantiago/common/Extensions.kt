package org.galio.bussantiago.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(itemHolder: Int): View =
  LayoutInflater.from(context).inflate(itemHolder, this, false)
