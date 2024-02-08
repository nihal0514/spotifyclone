package com.example.spotifyclone.screens.artist

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.TracksItem
import com.example.spotifyclone.screens.player.MediaPlayerConnection
import com.example.spotifyclone.screens.detail.movieImage
import com.example.spotifyclone.screens.detail.movieLink
import com.example.spotifyclone.screens.detail.movieName
import com.example.spotifyclone.viewModel.utils.LoadImageFromPlayListDetail
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import java.net.URLEncoder

@Composable
fun ArtistLibraryPlayList(context: Context, playListDetailList :List<TracksItem>, navController: NavController, musicViewModel: MusicPlayerViewModel) {
    val serviceIntent = musicViewModel.serviceIntent
    fun startProgressUpdates(isMusic: Boolean) {

        if(isMusic){
            serviceIntent?.action = "STOP_MUSIC"
            context.startService(serviceIntent)
        }
        serviceIntent?.action = "PLAY_MUSIC"
        context.startService(serviceIntent)
        if (serviceIntent != null) {
            context.bindService(serviceIntent, MediaPlayerConnection(), Context.BIND_AUTO_CREATE)
        }

        if(musicViewModel.IsPlaying){
            Log.d("IsPlaying", musicViewModel.IsPlaying.toString())
        }

    }
    DisposableEffect(Unit) {
        onDispose {

        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            items(playListDetailList.size) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(Color(0xff444473))
                        .clickable {
                            serviceIntent?.apply{
                                putExtra(
                                    "musicLink",
                                    playListDetailList[it].previewUrl,

                                    )
                                putExtra(
                                    "musicName",
                                    playListDetailList[it]?.name!!
                                )
                                putExtra(  "musicImage",playListDetailList[it].album?.images?.get(0)?.url!!)
                                putExtra(  "musicArtist",playListDetailList[it].artists?.get(0)?.name!!)
                                putExtra("IsPlaying",musicViewModel.IsPlaying)
                            }

                            Log.d("mmm", musicViewModel.MusicName)
                            if(musicViewModel.MusicName !="Ajj ke baad"){
                                musicViewModel.IsMusic = true;
                            }

                            musicViewModel.MusicName = playListDetailList[it]?.name!!
                            musicViewModel.MusicImage = playListDetailList[it]?.album?.images?.get(0)?.url.toString()
                            musicViewModel.MusicArtist = playListDetailList[it]?.artists?.get(0)?.name!!

                            startProgressUpdates(musicViewModel.IsMusic)

                            movieLink = URLEncoder.encode(playListDetailList[it]?.previewUrl)
                            movieName = URLEncoder.encode(playListDetailList[it]?.name!!)
                            movieImage = URLEncoder.encode(playListDetailList[it]?.album?.images?.get(0)?.url.toString())
                        },
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){

                            LoadImageFromPlayListDetail(imageUrl = playListDetailList[it]?.album?.images?.get(0)?.url.toString())
                            Text(
                                text = playListDetailList[it]?.name!!,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp),
                                fontSize = 12.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }

                        Row (
                            modifier= Modifier.padding(end = 20.dp)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.liked),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(
                                painter = painterResource(id = R.drawable.options),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(20.dp)
                            )
                        }


                    }
                }
            }
        }
    }
}