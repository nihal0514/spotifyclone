package com.example.spotifyclone.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.model.AlbumsItem
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val retrofitApiInterface: RetrofitApiInterface): ViewModel(){

    var popularAlbumList: List<AlbumsItem> by mutableStateOf(listOf())

    fun getPopularArtist(){
        viewModelScope.launch {
            try {
                val popularArtistResponseList =
                    retrofitApiInterface.getPopularArtist()
                popularAlbumList =
                    popularArtistResponseList.albums!!
                Log.d("mmm", popularAlbumList.toString())
            } catch (e: Exception) {

            }
        }
    }
}