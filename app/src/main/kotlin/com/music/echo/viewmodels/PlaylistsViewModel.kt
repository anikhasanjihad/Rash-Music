@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ahr.rashmusic.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import com.ahr.rashmusic.constants.AddToPlaylistSortDescendingKey
import com.ahr.rashmusic.constants.AddToPlaylistSortTypeKey
import com.ahr.rashmusic.constants.PlaylistSortType
import com.ahr.rashmusic.db.MusicDatabase
import com.ahr.rashmusic.extensions.toEnum
import com.ahr.rashmusic.utils.SyncUtils
import com.ahr.rashmusic.utils.dataStore
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PlaylistsViewModel
@Inject
constructor(
  @ApplicationContext context: Context,
  database: MusicDatabase,
  private val syncUtils: SyncUtils,
) : ViewModel() {
  val allPlaylists =
    context.dataStore.data
      .map {
        (try {
            it[AddToPlaylistSortTypeKey]
          } catch (e: Exception) {
            null
          })
          .toEnum(PlaylistSortType.CREATE_DATE) to
          ((try {
            it[AddToPlaylistSortDescendingKey]
          } catch (e: Exception) {
            null
          }) ?: true)
      }
      .distinctUntilChanged()
      .flatMapLatest { (sortType, descending) -> database.playlists(sortType, descending) }
      .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

  suspend fun sync() {
    syncUtils.syncSavedPlaylists()
  }
}
