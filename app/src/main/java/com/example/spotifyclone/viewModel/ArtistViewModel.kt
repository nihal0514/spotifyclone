package com.example.spotifyclone.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.model.AlbumsItem
import com.example.spotifyclone.model.ArtistDetailResponse
import com.example.spotifyclone.model.TracksItem
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(private val retrofitApiInterface: RetrofitApiInterface): ViewModel(){

    var artistDetail: ArtistDetailResponse by mutableStateOf(ArtistDetailResponse())
    var artistTopTracks: List<TracksItem> by mutableStateOf(listOf())

    fun getArtistData(id:String){
        viewModelScope.launch {
            try {
                val artistDetailResponse =
                    retrofitApiInterface.getArtistDetails(id)
                artistDetail =
                    artistDetailResponse
            } catch (e: Exception) {

            }
        }
    }


    fun getArtistsTopTracks(id:String){
        viewModelScope.launch {
            try {
                val artistTopTracksResponse =
                    retrofitApiInterface.getArtistTopTracks(id)
                artistTopTracks =
                    artistTopTracksResponse.tracks as List<TracksItem>
            } catch (e: Exception) {

            }
        }
    }
}