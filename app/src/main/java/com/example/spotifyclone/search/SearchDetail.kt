package com.example.spotifyclone.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyclone.model.ItemsItemsSearch
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import com.example.spotifyclone.viewModel.SearchViewModel
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDetail(homeNavController: NavController,musicPlayerViewModel: MusicPlayerViewModel){
    var offset by remember { mutableStateOf(0f) }
    var textState by remember { mutableStateOf(TextFieldValue()) }

    val searchListViewModel= hiltViewModel<SearchViewModel>()
    val searchItemsList: List<ItemsItemsSearch> = searchListViewModel.searchResultList

    Scaffold(
        topBar = {
                TextField(

                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedContainerColor = Color.Gray,
                        unfocusedContainerColor = Color.Gray
                    ),
                    value = textState,

                    onValueChange = {

                        textState = it
                        Log.d("textState", textState.text)
                        searchListViewModel.getSearchResult(textState.text)
                        Log.d("llllllllllllllllll", searchItemsList.toString())
                        /*   if(!textState.text.isEmpty()){
                               isSearch= true
                           }else{
                               isSearch=false
                           }*/

                    },
                    leadingIcon = {
                        Row(
                            modifier = Modifier.width(20.dp),
                            //   verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(5.dp))

                        }
                    },
                    label = {

                    },

                    modifier = Modifier
                        .fillMaxWidth()
                )
        }
    ) {
        it

        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black
            )
            .padding(horizontal = 10.dp)
            .scrollable(
                orientation = Orientation.Vertical,

                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
        ) {
            Column(){
                Spacer(modifier = Modifier.height(60.dp))
                searchItemList(context = LocalContext.current, playListDetailList =searchItemsList,musicPlayerViewModel)

            }
              }
    }

}