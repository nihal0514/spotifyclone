package com.example.spotifyclone

import android.app.Application
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.core.graphics.drawable.IconCompat
import dagger.hilt.android.HiltAndroidApp


object ShortcutConstants {
    const val SHORTCUT_ID = "myPlaylist"
    const val SHORTCUT_LABEL = "My Playlist"
    const val SHORTCUT_ACTION = "com.example.ACTION_SHORTCUT"
}
@HiltAndroidApp
class AppClass(): Application(){
    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun onCreate() {
        super.onCreate()
        registerShortcuts()
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private fun registerShortcuts() {

        val shortcutIntent = Intent(this, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            putExtra("openLibrary", true)
        }
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val shortcut = ShortcutInfo.Builder(this, ShortcutConstants.SHORTCUT_ID)
            .setShortLabel(ShortcutConstants.SHORTCUT_LABEL)
            .setIcon(Icon.createWithResource(this, R.drawable.demoimg))
            .setIntent(shortcutIntent)
            .build()
        shortcutManager?.dynamicShortcuts = listOf(shortcut)
    }
}