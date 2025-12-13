package org.galio.bussantiago.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colors from shared/src/main/res/values/colors.xml
private val ColorPrimary = Color(0xFF03A9F4) // Light Blue 500
private val ColorPrimaryDark = Color(0xFF0288D1) // Light Blue 700
private val ColorAccent = Color(0xFFFF6E40) // Deep Orange A200

// Theme.AppCompat.Light.DarkActionBar defaults
// Uses Material Design light theme colors:
// - textColorPrimary: 87% black (#DE000000)
// - background: light gray (#FAFAFA)
private val BackgroundLight = Color(0xFFFAFAFA)
private val SurfaceLight = Color(0xFFFFFFFF)
private val TextPrimary = Color(0xDE000000) // 87% alpha black - exact AppCompat value

private val LightColorPalette = lightColors(
  primary = ColorPrimary,
  primaryVariant = ColorPrimaryDark,
  secondary = ColorAccent,
  secondaryVariant = ColorAccent,
  background = BackgroundLight,
  surface = SurfaceLight,
  error = Color(0xFFB00020),
  onPrimary = Color.White,
  onSecondary = Color.White,
  onBackground = TextPrimary,
  onSurface = TextPrimary,
  onError = Color.White
)

@Composable
fun BusSantiagoTheme(content: @Composable () -> Unit) {
  MaterialTheme(colors = LightColorPalette) {
    Surface {
      content()
    }
  }
}
