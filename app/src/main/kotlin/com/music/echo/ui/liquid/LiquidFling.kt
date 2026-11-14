/*
 * Smooth, slightly longer glide for scrolling lists (iOS-like feel).
 */
package com.ahr.rashmusic.ui.liquid

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.math.abs

private class LiquidFlingBehavior(private val decay: DecayAnimationSpec<Float>) : FlingBehavior {
  override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
    if (abs(initialVelocity) <= 1f) return initialVelocity
    var lastValue = 0f
    var remaining = initialVelocity
    AnimationState(initialValue = 0f, initialVelocity = initialVelocity).animateDecay(decay) {
      val delta = value - lastValue
      val consumed = scrollBy(delta)
      lastValue = value
      remaining = velocity
      // Hit the start/end of the list: stop instead of fighting the edge.
      if (abs(delta - consumed) > 0.5f) cancelAnimation()
    }
    return remaining
  }
}

@Composable
fun rememberLiquidFling(): FlingBehavior = remember {
  LiquidFlingBehavior(exponentialDecay(frictionMultiplier = 0.8f))
}
