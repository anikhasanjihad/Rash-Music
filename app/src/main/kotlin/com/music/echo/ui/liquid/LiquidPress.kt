/*
 * Press-scale feedback inspired by Liquid Music Android (MIT, (c) admin0330).
 * See THIRD_PARTY_NOTICES.md.
 */
package com.ahr.rashmusic.ui.liquid

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Springs the element down slightly while a finger is on it. It only observes touches (never
 * consumes them), so it works on top of any existing clickable and never blocks scrolling.
 */
fun Modifier.liquidPress(pressedScale: Float = 0.97f): Modifier = composed {
  var pressed by remember { mutableStateOf(false) }
  val scale by
    animateFloatAsState(
      targetValue = if (pressed) pressedScale else 1f,
      animationSpec = LiquidMotion.responsiveSpring(),
      label = "liquid-press",
    )
  this.graphicsLayer {
      scaleX = scale
      scaleY = scale
    }
    .pointerInput(Unit) {
      awaitEachGesture {
        awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
        pressed = true
        do {
          val event = awaitPointerEvent(PointerEventPass.Final)
          val finished = event.changes.any { it.isConsumed } || event.changes.none { it.pressed }
        } while (!finished)
        pressed = false
      }
    }
}
