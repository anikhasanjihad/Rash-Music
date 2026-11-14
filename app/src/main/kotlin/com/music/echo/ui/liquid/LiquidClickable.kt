/*
 * Press-scale interaction adapted from Liquid Music Android (MIT, (c) admin0330).
 * See THIRD_PARTY_NOTICES.md.
 */
package echo.music.iad1tya.ui.liquid

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/** Clickable that springs down slightly while pressed (smooth, GPU-only animation). */
@Composable
fun Modifier.liquidClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier {
  val source = remember { MutableInteractionSource() }
  val pressed by source.collectIsPressedAsState()
  val scale by
    animateFloatAsState(
      targetValue = if (pressed && enabled) 0.965f else 1f,
      animationSpec = LiquidMotion.responsiveSpring(),
      label = "liquid-press-scale",
    )
  return this.graphicsLayer {
      scaleX = scale
      scaleY = scale
    }
    .clickable(enabled = enabled, interactionSource = source, indication = null, onClick = onClick)
}
