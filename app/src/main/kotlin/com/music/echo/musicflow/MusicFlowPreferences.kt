package com.ahr.rashmusic.musicflow

import androidx.datastore.preferences.core.Preferences
import com.ahr.rashmusic.constants.CanvasThumbnailAnimationKey
import com.ahr.rashmusic.constants.DataSaverEnabledKey
import com.ahr.rashmusic.constants.MusicFlowAnimatedBackgroundKey
import com.ahr.rashmusic.constants.MusicFlowAutoNextKey
import com.ahr.rashmusic.constants.MusicFlowAutoplayKey
import com.ahr.rashmusic.constants.MusicFlowCellularPrefetchKey
import com.ahr.rashmusic.constants.MusicFlowEnabledKey
import com.ahr.rashmusic.constants.MusicFlowHideRecentKey
import com.ahr.rashmusic.constants.MusicFlowLyricsKey

data class MusicFlowPreferences(
  val enabled: Boolean = true,
  val autoplay: Boolean = true,
  val autoNext: Boolean = true,
  val animatedBackground: Boolean = false,
  val lyrics: Boolean = true,
  val cellularPrefetch: Boolean = true,
  val hideRecent: Boolean = true,
  val dataSaver: Boolean = false,
) {
  companion object {
    fun from(preferences: Preferences) =
      MusicFlowPreferences(
        enabled = preferences[MusicFlowEnabledKey] ?: true,
        autoplay = preferences[MusicFlowAutoplayKey] ?: true,
        autoNext = preferences[MusicFlowAutoNextKey] ?: true,
        animatedBackground =
          preferences[MusicFlowAnimatedBackgroundKey]
            ?: preferences[CanvasThumbnailAnimationKey]
            ?: false,
        lyrics = preferences[MusicFlowLyricsKey] ?: true,
        cellularPrefetch = preferences[MusicFlowCellularPrefetchKey] ?: true,
        hideRecent = preferences[MusicFlowHideRecentKey] ?: true,
        dataSaver = preferences[DataSaverEnabledKey] ?: false,
      )
  }
}
