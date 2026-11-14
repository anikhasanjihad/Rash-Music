/*
 * Motion tokens adapted from Liquid Music Android (MIT, (c) admin0330).
 * See THIRD_PARTY_NOTICES.md.
 */
package echo.music.iad1tya.ui.liquid

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

object LiquidMotion {
  const val QUICK = 180
  const val STANDARD = 320
  const val DELIBERATE = 520

  fun <T> responsiveSpring() =
    spring<T>(dampingRatio = 0.82f, stiffness = Spring.StiffnessMediumLow)

  fun <T> gentleSpring() = spring<T>(dampingRatio = 0.88f, stiffness = Spring.StiffnessLow)
}
