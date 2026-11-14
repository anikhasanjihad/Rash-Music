package com.ahr.rashmusic.ui.screens

import android.app.Activity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.music.innertube.pages.PodcastPage
import com.ahr.rashmusic.constants.DarkModeKey
import com.ahr.rashmusic.constants.PureBlackKey
import com.ahr.rashmusic.echomusic.changelog.ChangelogScreen
import com.ahr.rashmusic.echomusic.commitscreen.CommitScreen
import com.ahr.rashmusic.echomusic.updater.UpdateScreen
import com.ahr.rashmusic.ui.screens.ambient.AmbientModeScreen
import com.ahr.rashmusic.ui.screens.artist.ArtistAlbumsScreen
import com.ahr.rashmusic.ui.screens.artist.ArtistItemsScreen
import com.ahr.rashmusic.ui.screens.artist.ArtistScreen
import com.ahr.rashmusic.ui.screens.artist.ArtistSongsScreen
import com.ahr.rashmusic.ui.screens.equalizer.EqScreen
import com.ahr.rashmusic.ui.screens.equalizer.axion.AxionEqScreen
import com.ahr.rashmusic.ui.screens.generate.GenerateScreen
import com.ahr.rashmusic.ui.screens.library.LibraryScreen
import com.ahr.rashmusic.ui.screens.library.LocalSongScreen
import com.ahr.rashmusic.ui.screens.musicflow.MusicFlowScreen
import com.ahr.rashmusic.ui.screens.playlist.AutoPlaylistScreen
import com.ahr.rashmusic.ui.screens.playlist.BottomPlaylistScreen
import com.ahr.rashmusic.ui.screens.playlist.CachePlaylistScreen
import com.ahr.rashmusic.ui.screens.playlist.LocalPlaylistScreen
import com.ahr.rashmusic.ui.screens.playlist.OnlinePlaylistScreen
import com.ahr.rashmusic.ui.screens.playlist.SpotifyPlaylistDetailScreen
import com.ahr.rashmusic.ui.screens.playlist.TopPlaylistScreen
import com.ahr.rashmusic.ui.screens.recognition.RecognitionHistoryScreen
import com.ahr.rashmusic.ui.screens.recognition.RecognitionScreen
import com.ahr.rashmusic.ui.screens.search.OnlineSearchResult
import com.ahr.rashmusic.ui.screens.search.SearchScreen
import com.ahr.rashmusic.ui.screens.settings.AboutScreen
import com.ahr.rashmusic.ui.screens.settings.AccountSettingsScreen
import com.ahr.rashmusic.ui.screens.settings.AiSettings
import com.ahr.rashmusic.ui.screens.settings.AppIconSettingsScreen
import com.ahr.rashmusic.ui.screens.settings.AppearanceSettings
import com.ahr.rashmusic.ui.screens.settings.BackupAndRestore
import com.ahr.rashmusic.ui.screens.settings.BlockedArtistsScreen
import com.ahr.rashmusic.ui.screens.settings.ContentSettings
import com.ahr.rashmusic.ui.screens.settings.DarkMode
import com.ahr.rashmusic.ui.screens.settings.GlassEffectSettings
import com.ahr.rashmusic.ui.screens.settings.MusicFlowSettings
import com.ahr.rashmusic.ui.screens.settings.PlayerSettings
import com.ahr.rashmusic.ui.screens.settings.PrivacySettings
import com.ahr.rashmusic.ui.screens.settings.SettingsScreen
import com.ahr.rashmusic.ui.screens.settings.StorageSettings
import com.ahr.rashmusic.ui.screens.settings.ThemeScreen
import com.ahr.rashmusic.ui.screens.settings.UpdateSettings
import com.ahr.rashmusic.ui.screens.settings.UptimeScreen
import com.ahr.rashmusic.ui.screens.settings.integrations.ListenTogetherSettings
import com.ahr.rashmusic.utils.rememberEnumPreference
import com.ahr.rashmusic.utils.rememberPreference

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.navigationBuilder(
  navController: NavHostController,
  scrollBehavior: TopAppBarScrollBehavior,
  activity: Activity,
  snackbarHostState: SnackbarHostState
) {
  composable(Screens.Home.route) {
    HomeScreen(navController = navController, snackbarHostState = snackbarHostState)
  }

  composable(Screens.Search.route) {
    val pureBlackEnabled by rememberPreference(PureBlackKey, defaultValue = false)
    val darkTheme by rememberEnumPreference(DarkModeKey, defaultValue = DarkMode.AUTO)
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val useDarkTheme =
      remember(darkTheme, isSystemInDarkTheme) {
        if (darkTheme == DarkMode.AUTO) isSystemInDarkTheme else darkTheme == DarkMode.ON
      }
    val pureBlack = remember(pureBlackEnabled, useDarkTheme) { pureBlackEnabled && useDarkTheme }
    SearchScreen(navController = navController, pureBlack = pureBlack)
  }

  composable(Screens.Library.route) { LibraryScreen(navController) }

  composable(Screens.ListenTogether.route) {
    ListenTogetherScreen(navController, showTopBar = false)
  }

  composable(
    route = "listen_together_from_topbar",
  ) {
    ListenTogetherScreen(navController, showTopBar = true)
  }

  composable("listen_together/chat") { CommentTogetherScreen(navController) }

  composable("history") { HistoryScreen(navController) }

  composable("ambient_mode") { AmbientModeScreen(navController) }

  composable("music_flow") { MusicFlowScreen(navController) }

  composable(
    route = "podcast_video",
    enterTransition = { fadeIn(tween(300)) },
    exitTransition = { fadeOut(tween(300)) },
    popEnterTransition = { fadeIn(tween(300)) },
    popExitTransition = { fadeOut(tween(300)) },
  ) {
    com.ahr.rashmusic.ui.screens.podcast.PodcastVideoScreen(navController)
  }

  composable("local_songs") { LocalSongScreen(navController) }

  composable("stats") { StatsScreen(navController) }

  composable("wrapped") { com.music.echo.ui.screens.wrapped.WrappedScreen(navController) }

  composable("mood_and_genres") { MoodAndGenresScreen(navController, scrollBehavior) }

  composable("account") { AccountScreen(navController, scrollBehavior) }

  composable("new_release") { NewReleaseScreen(navController, scrollBehavior) }

  composable("charts_screen") { ChartsScreen(navController) }

  composable(
    route = "browse/{browseId}",
    arguments = listOf(navArgument("browseId") { type = NavType.StringType })
  ) {
    BrowseScreen(navController, scrollBehavior, it.arguments?.getString("browseId"))
  }

  composable(
    route = "search/{query}",
    arguments =
      listOf(
        navArgument("query") { type = NavType.StringType },
      ),
    enterTransition = { fadeIn(tween(250)) },
    exitTransition = {
      if (targetState.destination.route?.startsWith("search/") == true) {
        fadeOut(tween(200))
      } else {
        fadeOut(tween(200)) + slideOutHorizontally { -it / 2 }
      }
    },
    popEnterTransition = {
      if (initialState.destination.route?.startsWith("search/") == true) {
        fadeIn(tween(250))
      } else {
        fadeIn(tween(250)) + slideInHorizontally { -it / 2 }
      }
    },
    popExitTransition = { fadeOut(tween(200)) },
  ) {
    OnlineSearchResult(navController)
  }

  composable(
    route = "album/{albumId}",
    arguments =
      listOf(
        navArgument("albumId") { type = NavType.StringType },
      ),
  ) {
    AlbumScreen(navController, scrollBehavior)
  }

  composable(
    route = "artist/{artistId}",
    arguments =
      listOf(
        navArgument("artistId") { type = NavType.StringType },
      ),
  ) { backStackEntry ->
    // A podcast episode's "artist" is its show (an MPSP browse id), which is a playlist.
    val artistId = backStackEntry.arguments?.getString("artistId")
    if (PodcastPage.isShowBrowseId(artistId)) {
      LaunchedEffect(artistId) {
        navController.navigate("online_playlist/${PodcastPage.showId(artistId!!)}") {
          popUpTo(backStackEntry.destination.id) { inclusive = true }
        }
      }
    } else {
      ArtistScreen(navController, scrollBehavior)
    }
  }

  composable(
    route = "artist/{artistId}/songs",
    arguments =
      listOf(
        navArgument("artistId") { type = NavType.StringType },
      ),
  ) {
    ArtistSongsScreen(navController, scrollBehavior)
  }

  composable(
    route = "artist/{artistId}/albums",
    arguments = listOf(navArgument("artistId") { type = NavType.StringType })
  ) {
    ArtistAlbumsScreen(navController, scrollBehavior)
  }

  composable(
    route = "artist/{artistId}/items?browseId={browseId}?params={params}",
    arguments =
      listOf(
        navArgument("artistId") { type = NavType.StringType },
        navArgument("browseId") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("params") {
          type = NavType.StringType
          nullable = true
        },
      ),
  ) {
    ArtistItemsScreen(navController, scrollBehavior)
  }

  composable(
    route = "online_playlist/{playlistId}",
    arguments =
      listOf(
        navArgument("playlistId") { type = NavType.StringType },
      ),
  ) {
    OnlinePlaylistScreen(navController, scrollBehavior)
  }

  composable(
    route = "spotify_playlist/{playlistId}?title={title}&cover={cover}",
    arguments =
      listOf(
        navArgument("playlistId") { type = NavType.StringType },
        navArgument("title") {
          type = NavType.StringType
          defaultValue = "Playlist"
        },
        navArgument("cover") {
          type = NavType.StringType
          defaultValue = ""
        },
      ),
  ) { backStackEntry ->
    val playlistId = backStackEntry.arguments?.getString("playlistId") ?: return@composable
    val title = backStackEntry.arguments?.getString("title") ?: "Playlist"
    val cover = backStackEntry.arguments?.getString("cover") ?: ""
    SpotifyPlaylistDetailScreen(
      navController = navController,
      scrollBehavior = scrollBehavior,
      playlistId = playlistId,
      playlistTitle = title,
      playlistCover = cover,
    )
  }

  composable(
    route = "local_playlist/{playlistId}",
    arguments =
      listOf(
        navArgument("playlistId") { type = NavType.StringType },
      ),
  ) {
    LocalPlaylistScreen(navController, scrollBehavior)
  }

  composable(
    route = "auto_playlist/{playlist}",
    arguments =
      listOf(
        navArgument("playlist") { type = NavType.StringType },
      ),
  ) {
    AutoPlaylistScreen(navController, scrollBehavior)
  }

  composable(
    route = "cache_playlist/{playlist}",
    arguments =
      listOf(
        navArgument("playlist") { type = NavType.StringType },
      ),
  ) {
    CachePlaylistScreen(navController, scrollBehavior)
  }

  composable(
    route = "top_playlist/{top}",
    arguments =
      listOf(
        navArgument("top") { type = NavType.StringType },
      ),
  ) {
    TopPlaylistScreen(navController, scrollBehavior)
  }

  composable(
    route = "bottom_playlist/{bottom}",
    arguments =
      listOf(
        navArgument("bottom") { type = NavType.StringType },
      ),
  ) {
    BottomPlaylistScreen(navController, scrollBehavior)
  }

  composable(
    route = "youtube_browse/{browseId}?params={params}",
    arguments =
      listOf(
        navArgument("browseId") {
          type = NavType.StringType
          nullable = true
        },
        navArgument("params") {
          type = NavType.StringType
          nullable = true
        },
      ),
  ) {
    YouTubeBrowseScreen(navController)
  }

  composable("settings") { SettingsScreen(navController, scrollBehavior) }
  composable("blocked_artists") { BlockedArtistsScreen(navController) }

  composable(
    route = "settings/update?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    UpdateSettings(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable(
    route = "settings/account?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    AccountSettingsScreen(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable(
    route = "settings/integrations?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    AccountSettingsScreen(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey") ?: "Integrations"
    )
  }

  composable("ambient_settings") {
    com.ahr.rashmusic.ui.screens.settings.AmbientSettingsScreen(navController)
  }

  composable(
    route = "settings/appearance?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    AppearanceSettings(
      navController,
      scrollBehavior,
      activity,
      snackbarHostState,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable("settings/appearance/theme") { ThemeScreen(navController) }
  composable("settings/music_flow") { MusicFlowSettings(navController, scrollBehavior) }
  composable(
    route = "settings/appearance/haptics?highlightKey={highlightKey}",
    arguments = listOf(navArgument("highlightKey") { type = NavType.StringType; nullable = true })
  ) { backStackEntry ->
    com.ahr.rashmusic.ui.screens.settings.HapticsSettings(
      navController = navController,
      scrollBehavior = scrollBehavior,
      activity = activity,
      snackbarHostState = snackbarHostState,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable("settings/appearance/app_icon") {
    AppIconSettingsScreen(navController, activity, snackbarHostState)
  }

  composable("settings/appearance/font") {
    com.music.echo.ui.screens.settings.FontSelectionScreen(navController, scrollBehavior)
  }

  composable("settings/listening_summary") {
    com.music.echo.ui.screens.ListeningSummaryScreen(navController)
  }

  composable(
    route = "detailed_listening_history/{startTimestamp}",
    arguments =
      listOf(
        navArgument("startTimestamp") { type = NavType.StringType },
      ),
  ) {
    com.ahr.rashmusic.ui.screens.DetailedListeningHistoryScreen(navController)
  }

  composable("settings/appearance/liquidglass") {
    GlassEffectSettings(navController, scrollBehavior)
  }

  composable(
    route = "settings/content?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    ContentSettings(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable("uptime") { UptimeScreen(navController, scrollBehavior) }

  composable(
    route = "settings/ai?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    AiSettings(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable(
    route = "settings/player?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    PlayerSettings(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable(
    route =
      "settings/storage?autoOpenExportPicker={autoOpenExportPicker}&highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("autoOpenExportPicker") {
          type = NavType.BoolType
          defaultValue = false
        },
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    val autoOpenExportPicker = backStackEntry.arguments?.getBoolean("autoOpenExportPicker") ?: false
    StorageSettings(
      navController = navController,
      scrollBehavior = scrollBehavior,
      autoOpenExportPicker = autoOpenExportPicker,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable("settings/equalizer") { AxionEqScreen(onBackClick = { navController.navigateUp() }) }

  composable(
    route = "settings/privacy?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    PrivacySettings(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable(
    route = "settings/backup_restore?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    BackupAndRestore(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable("settings/extensions") {
    com.music.echo.ui.screens.settings.ExtensionSettingsScreen(
        navController = navController,
        scrollBehavior = scrollBehavior
    )
  }
  composable("settings/discord") {
    com.ahr.rashmusic.ui.screens.settings.DiscordSettings(navController, scrollBehavior)
  }

  composable("settings/lastfm") {
    com.ahr.rashmusic.ui.screens.settings.LastFMSettingsScreen(navController)
  }

  composable("settings/discord/experimental") {
    com.ahr.rashmusic.ui.screens.settings.DiscordExperimental(navController)
  }

  composable("settings/spotify_import") { SpotifyImportScreen(navController) }

  composable(route = "settings/integrations/listen_together") {
    ListenTogetherSettings(navController, scrollBehavior)
  }

  composable(
    route = "settings/about?highlightKey={highlightKey}",
    arguments =
      listOf(
        navArgument("highlightKey") {
          type = NavType.StringType
          nullable = true
        }
      )
  ) { backStackEntry ->
    AboutScreen(
      navController,
      scrollBehavior,
      highlightKey = backStackEntry.arguments?.getString("highlightKey")
    )
  }

  composable("update") { UpdateScreen(navController) }

  composable("login") { LoginScreen(navController) }

  dialog("equalizer") { EqScreen(navController = navController) }

  composable("recognition") { RecognitionScreen(navController) }

  composable("recognition_history") { RecognitionHistoryScreen(navController) }
  composable("settings/changelog") { ChangelogScreen(navController, scrollBehavior) }
  composable("settings/commits") { CommitScreen(navController, scrollBehavior) }
  composable("generate") { GenerateScreen(navController) }

  composable("settings/navidrome_login") {
    val context = androidx.compose.ui.platform.LocalContext.current
    val entryPoint =
      dagger.hilt.android.EntryPointAccessors.fromApplication(
        context.applicationContext,
        com.music.echo.cloud.CloudEntryPoint::class.java
      )
    com.music.echo.ui.screens.settings.cloud.NavidromeLoginScreen(navController, entryPoint.navidromeRepository())
  }

  composable("settings/navidrome_dashboard") {
    val context = androidx.compose.ui.platform.LocalContext.current
    val entryPoint =
      dagger.hilt.android.EntryPointAccessors.fromApplication(
        context.applicationContext,
        com.music.echo.cloud.CloudEntryPoint::class.java
      )
    com.music.echo.ui.screens.settings.cloud.NavidromeDashboardScreen(navController, entryPoint.navidromeRepository())
  }

  composable("settings/jellyfin_login") {
    val context = androidx.compose.ui.platform.LocalContext.current
    val entryPoint =
      dagger.hilt.android.EntryPointAccessors.fromApplication(
        context.applicationContext,
        com.music.echo.cloud.CloudEntryPoint::class.java
      )
    com.music.echo.ui.screens.settings.cloud.JellyfinLoginScreen(navController, entryPoint.jellyfinRepository())
  }

  composable("settings/jellyfin_dashboard") {
    val context = androidx.compose.ui.platform.LocalContext.current
    val entryPoint =
      dagger.hilt.android.EntryPointAccessors.fromApplication(
        context.applicationContext,
        com.music.echo.cloud.CloudEntryPoint::class.java
      )
    com.music.echo.ui.screens.settings.cloud.JellyfinDashboardScreen(navController, entryPoint.jellyfinRepository())
  }
}
