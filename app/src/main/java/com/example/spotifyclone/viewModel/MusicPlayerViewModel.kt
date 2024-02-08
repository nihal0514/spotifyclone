package com.example.spotifyclone.viewModel

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MusicPlayerViewModel @Inject constructor():ViewModel() {
    var serviceIntent: Intent? = null
    var MusicName by mutableStateOf("Ajj ke baad")
    var MusicArtist by mutableStateOf("Manan Bhardwaj")
    var MusicImage by mutableStateOf("https://i.scdn.co/image/ab67616d0000b273273fea9310ab7ae9d7791d6e")
    var IsPlaying by mutableStateOf(true)
    var IsMusic by mutableStateOf(false)
}