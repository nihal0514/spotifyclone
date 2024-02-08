package com.example.spotifyclone.viewModel

import android.app.appsearch.SearchResult
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.model.ItemsItemsSearch
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val retrofitApiInterface: RetrofitApiInterface): ViewModel(){

    var searchResultList: List<ItemsItemsSearch> by mutableStateOf(listOf())

    fun getSearchResult(songName: String){
        viewModelScope.launch {
            try {
                val searchItems = retrofitApiInterface.getTrackSearch(songName)
                Log.d("search",searchItems.toString())
                searchResultList = (searchItems.tracks?.items as List<ItemsItemsSearch>?)!!
            } catch (e: Exception) {

            }
        }
    }


}