package com.example.spotifyclone.screens.library

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.ItemsItemDetail
import com.example.spotifyclone.screens.detail.movieImage
import com.example.spotifyclone.screens.detail.movieLink
import com.example.spotifyclone.screens.detail.movieName
import com.example.spotifyclone.screens.player.MediaPlayerConnection
import com.example.spotifyclone.screens.player.mediaPlayerBinder
import com.example.spotifyclone.service.MusicService
import com.example.spotifyclone.viewModel.utils.LoadImageFromPlayListDetail
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import com.example.spotifyclone.viewModel.utils.LoadImageFromInternet
import com.example.spotifyclone.viewModel.utils.LoadImageFromInternetPlaylist
import java.net.URLEncoder

@Composable
fun libraryPlaylist(context: Context, playListDetailList :List<ItemsItemDetail>, name: String,image: String,bgColor: Color,navController: NavController, musicViewModel: MusicPlayerViewModel) {

    fun startProgressUpdates(isMusic: Boolean) {

        if(isMusic){
            musicViewModel.serviceIntent?.action = "STOP_MUSIC"
            context.startService(musicViewModel.serviceIntent)
        }

        musicViewModel.serviceIntent?.action = "PLAY_MUSIC"
        context.startService(musicViewModel.serviceIntent )

        Log.d("mposition",mediaPlayerBinder?.IsPlayingProgress().toString())
        if (musicViewModel.serviceIntent != null) {
            context.bindService(musicViewModel.serviceIntent!!, MediaPlayerConnection(),Context.BIND_AUTO_CREATE)
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

            item {
                Column(
                    modifier= Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                               bgColor,
                                Color(0xff121212)

                            ),
                            start = Offset(0.0f, 50.0f),
                            end= Offset(0.0f, 800.0f)

                            )
                    )
                ) {

                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 10.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        tint = Color.White
                    )
                    /*Row(
                    ) {
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
                                .fillMaxWidth()
                                .height(20.dp)
                                .padding(horizontal = 10.dp)
                                .background(color = Color(0x1AFFFFFF))
                        )

                    }*/
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                    //    Log.d("imageUrl",playListDetailList[0].track?.album?.images?.get(0)?.url!!)
                        LoadImageFromInternetPlaylist(imageUrl = image)
                    }
                    Text(
                        name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = 20.dp,
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 2.dp
                        ),
                    )
                    Text(
                        "266 songs", fontSize = 12.sp, color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .padding(horizontal = 10.dp),
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
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
                }

            }
            items(playListDetailList.size) {
                val lyricsString= mutableListOf<String>()

                playListDetailList[it].track?.artists?.forEach { it
                    lyricsString.add(it?.name.toString())
                }
                val stringResult = lyricsString.joinToString(",")

                Box(
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp,
                            //  vertical = 5.dp
                        )
                        .background(Color(0xff121212))
                        .clickable {
                            if(musicViewModel.serviceIntent == null){
                                musicViewModel.serviceIntent = Intent(context, MusicService::class.java)
                            }
                            musicViewModel.serviceIntent?.apply {
                                putExtra(
                                    "musicLink",
                                    playListDetailList[it].track?.previewUrl
                                )
                                putExtra(
                                    "musicName",
                                    playListDetailList[it].track?.name!!
                                )
                                putExtra(
                                    "musicImage",
                                    playListDetailList[it].track?.album?.images?.get(0)?.url!!
                                )
                                putExtra(
                                    "musicArtist",
                                    playListDetailList[it].track?.artists?.get(0)?.name!!
                                )
                                putExtra("IsPlaying", musicViewModel.IsPlaying)
                            }

                            Log.d("mmm", musicViewModel.MusicName)
                            if (musicViewModel.MusicName != "Ajj ke baad") {
                                musicViewModel.IsMusic = true;
                            }

                            musicViewModel.MusicName = playListDetailList[it].track?.name!!
                            musicViewModel.MusicImage =
                                playListDetailList[it].track?.album?.images?.get(0)?.url!!
                            musicViewModel.MusicArtist =
                                playListDetailList[it].track?.artists?.get(0)?.name!!

                            startProgressUpdates(musicViewModel.IsMusic)

                            movieLink = URLEncoder.encode(playListDetailList[it].track?.previewUrl)
                            movieName = URLEncoder.encode(playListDetailList[it].track?.name!!)
                            movieImage =
                                URLEncoder.encode(playListDetailList[it].track?.album?.images?.get(0)?.url!!)
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

                            LoadImageFromPlayListDetail(imageUrl = playListDetailList[it].track?.album?.images?.get(0)?.url!!)
                            Column {
                                Text(
                                    text = playListDetailList[it].track?.name!!,
                                    fontWeight = FontWeight.Bold,

                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .width(250.dp),
                                    fontSize = 14.sp,

                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                    )
                                Spacer(modifier = Modifier.height(5.dp))
                                Row(
                                    modifier = Modifier.padding(horizontal = 5.dp)

                                ){
                                    Box(
                                        modifier= Modifier
                                            .background(
                                                color = Color.Gray
                                            )
                                            .width(35.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "LYRICS",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            color = Color.Black,
                                        )

                                    }
                                    Text(
                                        text = stringResult,
                                        fontWeight = FontWeight.Bold,

                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .width(215.dp),
                                        fontSize = 10.sp,

                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }
                            }
                        }
                        Row (
                            modifier= Modifier.padding(end = 20.dp)
                        ){
                            /*Image(

                                painter = painterResource(id = R.drawable.liked),

                                contentDescription = null,

                                modifier = Modifier
                                    .height(15.dp)
                                    .width(20.dp)
                            )*/
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(

                                painter = painterResource(id = R.drawable.more_option),

                                contentDescription = null,

                                modifier = Modifier
                                    .height(16.dp)
                                    .width(20.dp)

                            )
                        }


                    }
                }
            }
        }
    }
}