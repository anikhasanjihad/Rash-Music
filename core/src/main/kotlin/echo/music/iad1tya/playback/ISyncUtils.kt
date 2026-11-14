package com.ahr.rashmusic.playback

import com.ahr.rashmusic.db.entities.SongEntity

interface ISyncUtils {
  fun likeSong(song: SongEntity)
}
