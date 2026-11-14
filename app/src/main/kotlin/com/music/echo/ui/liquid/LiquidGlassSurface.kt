/*
 * Glass surface adapted from Liquid Music Android (MIT, (c) admin0330).
 * Simplified: translucent tint + hairline border + slow moving highlight.
 * See THIRD_PARTY_NOTICES.md.
 */
package com.ahr.rashmusic.ui.liquid

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LiquidGlassSurface(
  modifier: Modifier = Modifier,
  cornerRadius: Dp = 24.dp,
  opacity: Float = 0.72f,
  tintColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
  elevation: Dp = 10.dp,
  content: @Composable BoxScope.() -> Unit,
) {
  val shape = RoundedCornerShape(cornerRadius)
  val transition = rememberInfiniteTransition(label = "liquid-glass-light")
  val light by
    transition.animateFloat(
      initialValue = -0.25f,
      targetValue = 1.25f,
      animationSpec =
        infiniteRepeatable(
          animation = tween(LiquidMotion.DELIBERATE * 8),
          repeatMode = RepeatMode.Restart,
        ),
      label = "liquid-glass-light-position",
    )
  Box(
    modifier =
      modifier
        .shadow(elevation, shape, ambientColor = Color.Black.copy(0.22f), spotColor = Color.Black.copy(0.18f))
        .clip(shape)
        .background(tintColor.copy(alpha = opacity.coerceIn(0f, 1f)), shape)
        .border(BorderStroke(0.7.dp, Color.White.copy(alpha = 0.22f)), shape)
        .background(
          brush =
            Brush.linearGradient(
              colorStops =
                arrayOf(
                  0f to Color.Transparent,
                  light.coerceIn(0f, 1f) to Color.White.copy(alpha = 0.10f),
                  1f to Color.Transparent,
                ),
              start = Offset.Zero,
              end = Offset.Infinite,
            ),
          shape = shape,
        ),
    content = content,
  )
}
