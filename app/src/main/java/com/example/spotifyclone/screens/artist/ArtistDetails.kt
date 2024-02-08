package com.example.spotifyclone.screens.artist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.ArtistDetailResponse
import com.example.spotifyclone.model.TracksItem
import com.example.spotifyclone.viewModel.utils.LoadArtistImageFromInternet
import com.example.spotifyclone.viewModel.ArtistViewModel
import com.example.spotifyclone.viewModel.MusicPlayerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistDetailScreen(id: String, name: String,navController: NavController,musicViewModel: MusicPlayerViewModel){

    val artistViewModel= hiltViewModel<ArtistViewModel>()
    artistViewModel.getArtistData(id)
    artistViewModel.getArtistsTopTracks(id)
    val artistDetail: ArtistDetailResponse = artistViewModel.artistDetail
    val artistTopTracks: List<TracksItem> = artistViewModel.artistTopTracks


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
        Box(){
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription =null,modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp), tint = Color.White )

            Column {
                    LoadArtistImageFromInternet(artistDetail?.images?.get(0)?.url.toString())
                Column() {
                    Text(name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 2.dp).clickable {
                        Log.d("imagessssssssssssssss",artistDetail?.images?.get(0)?.url.toString())
                    },)
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
                            painter = painterResource(id = R.drawable.shuffle),
                            contentDescription = null,
                            modifier = Modifier
                                .height(50.dp)
                                .width(60.dp)
                        )

                    }
                       ArtistLibraryPlayList(LocalContext.current,artistTopTracks,navController,musicViewModel)

                }
            }
        }


    }



}


