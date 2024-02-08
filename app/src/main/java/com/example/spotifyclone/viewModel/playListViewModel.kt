package com.example.spotifyclone.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.model.AlbumsItem
import com.example.spotifyclone.model.ArtistsItem
import com.example.spotifyclone.model.ItemsItemDetail
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(private val retrofitApiInterface: RetrofitApiInterface): ViewModel(){
    var myPlaylist: List<PlaylistItems> by mutableStateOf(listOf())
    var myPlaylistDetail: List<ItemsItemDetail> by mutableStateOf(listOf())
    var popularPlayList: List<PlaylistItems> by mutableStateOf(listOf())
    var popularArtistList: List<ArtistsItem> by mutableStateOf(listOf())


    fun getMyPlaylist(){
        viewModelScope.launch {
            try {
                val playlistItems =
                    retrofitApiInterface.getMyPlaylist()
                myPlaylist = (playlistItems.items as List<PlaylistItems>?)!!
            } catch (e: Exception) {

            }
        }
    }


    fun getMyPlaylistDetail(id:String){
        viewModelScope.launch {
            try {
                val mPlayListDetailList =
                    retrofitApiInterface.getMyPlaylistDetail(id)
                myPlaylistDetail = mPlayListDetailList.items!!
            } catch (e: Exception) {

            }
        }
    }

    fun getPopularPlaylist(){
        viewModelScope.launch {
            try{
                val popularPlaylistsList= retrofitApiInterface.getPopularPlaylists()
                popularPlayList=
                    (popularPlaylistsList.playlists?.items as List<PlaylistItems>?)!!

            }catch (e: Exception){

            }
        }
    }
    fun getPopularArtist(){
        viewModelScope.launch {
            try{
                val popularArtistListRes= retrofitApiInterface.getPopularArtists()
                popularArtistList=
                    (popularArtistListRes.artists as List<ArtistsItem>?)!!

            }catch (e: Exception){

            }
        }
    }


}