package com.example.spotifyclone.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.ItemsItemDetail
import com.example.spotifyclone.viewModel.PlayListViewModel
import androidx.compose.runtime.*
import com.example.spotifyclone.screens.library.libraryPlaylist
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
var movieLink by mutableStateOf("")
var movieName by mutableStateOf("")
var movieImage by mutableStateOf("")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(id: String,name: String, navController: NavController,musicViewModel: MusicPlayerViewModel){

    val playListViewModel= hiltViewModel<PlayListViewModel>()
    val playListDetailList: List<ItemsItemDetail> = playListViewModel.myPlaylistDetail


    var textState by remember { mutableStateOf(TextFieldValue()) }


    LaunchedEffect(key1 = true){

        playListViewModel.getMyPlaylistDetail(id)
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff3B13B0),
                        Color(0xff271363),
                        Color(0xff1B1235),
                        Color(0xff121212)
                    ),

                    )
            )
    ){

       Column() {
           Icon(imageVector = Icons.Default.ArrowBack, contentDescription =null,modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp).clickable{
                                                                                                                                                           navController.popBackStack()
           }, tint = Color.White )
           Row (
               ){
               TextField(
                   value = textState,
                   onValueChange = { textState = it },

                   label = {
                       Row(
                           modifier = Modifier
                               .fillMaxWidth()
                               .background(color = Color(0x1AFFFFFF)),
                           verticalAlignment = Alignment.CenterVertically
                       ) {

                           Icon(
                               imageVector = Icons.Default.Search,
                               contentDescription = null,
                               tint = Color.Black,
                               modifier = Modifier
                                   .width(20.dp)
                                   .height(20.dp)
                           )
                           Spacer(modifier = Modifier.width(5.dp))
                           Text(
                               "Find in playlist songs",
                               fontSize = 11.sp,

                           )
                       }
                   },
                   modifier = Modifier
                       .width(310.dp)
                       .height(50.dp)
                       .padding(horizontal = 10.dp)
                       .background(color = Color(0x1AFFFFFF))
               )
               Box(
                   modifier = Modifier
                       .clickable { }
                       .width(60.dp)
                       .height(50.dp)
                       .background(
                           color = Color(0x1AFFFFFF),
                           shape = RoundedCornerShape(size = 2.dp)
                       )
               ) {
                   Text(
                       text = "Sort",
                       color= Color.White,
                       fontSize= 11.sp,
                       modifier = Modifier.padding(16.dp)
                       // Adjust padding as needed
                   )
               }
           }
           Text(name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 2.dp),)
           Text("266 songs", fontSize = 12.sp, color = Color.Gray, modifier = Modifier
               .fillMaxWidth()
               .height(15.dp)
               .padding(horizontal = 10.dp),)
           Row(
               horizontalArrangement = Arrangement.SpaceBetween,
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(horizontal = 10.dp, vertical = 10.dp)
           ){
               Image(
                   painter = painterResource(id = R.drawable.downloaded),
                   contentDescription = null,
                   modifier = Modifier
                       .height(25.dp)
                       .width(30.dp)
               )
               Image(
                   painter = painterResource(id = R.drawable.shuffle_detail),
                   contentDescription = null,
                   modifier = Modifier
                       .height(50.dp)
                       .width(60.dp)
               )

           }
           libraryPlaylist(LocalContext.current,playListDetailList,navController,musicViewModel)

       }
    }

}






