package com.example.spotifyclone.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.model.ArtistDetailResponse
import com.example.spotifyclone.model.CurrentProfileresponse
import com.example.spotifyclone.network.RetrofitApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val retrofitApiInterface: RetrofitApiInterface): ViewModel() {

    var userDetail: CurrentProfileresponse by mutableStateOf(CurrentProfileresponse())

    fun getCurrentUser(){
        viewModelScope.launch {
            try {
                val currentUserResponse =
                    retrofitApiInterface.getCurrentUserProfile()
                userDetail =
                    currentUserResponse
            } catch (e: Exception) {

            }
        }
    }


}