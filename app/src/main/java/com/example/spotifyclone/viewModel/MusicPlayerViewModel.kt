package com.example.spotifyclone.viewModel

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class MusicPlayerViewModel @Inject constructor():ViewModel() {
    var serviceIntent by mutableStateOf<Intent?>(null)
    var MusicName by mutableStateOf("Ajj ke baad")
    var MusicArtist by mutableStateOf("Manan Bhardwaj")
    var MusicImage by mutableStateOf("https://i.scdn.co/image/ab67616d0000b273273fea9310ab7ae9d7791d6e")
    var IsPlaying by mutableStateOf(true)
    var IsMusic by mutableStateOf(false)
    var albumImageBitmap by mutableStateOf<Bitmap?>(null)
    var playListImageBitmap by mutableStateOf<Bitmap?>(null)
    var palette by mutableStateOf<Palette?>(null)
    var playlistPalette by mutableStateOf<Palette?>(null)

    suspend fun getBitmapFromUrl(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = URL(imageUrl).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}