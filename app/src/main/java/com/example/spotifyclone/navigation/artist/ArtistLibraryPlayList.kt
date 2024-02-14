package com.example.spotifyclone.navigation.artist

import android.content.Context
import android.icu.text.UnicodeSet.SpanCondition
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.ArtistDetailResponse
import com.example.spotifyclone.model.TracksItem
import com.example.spotifyclone.screens.player.MediaPlayerConnection
import com.example.spotifyclone.screens.detail.movieImage
import com.example.spotifyclone.screens.detail.movieLink
import com.example.spotifyclone.screens.detail.movieName
import com.example.spotifyclone.viewModel.utils.LoadImageFromPlayListDetail
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import com.example.spotifyclone.viewModel.utils.LoadArtistImageFromInternet
import java.net.URLEncoder

@Composable
fun ArtistLibraryPlayList(context: Context,name: String,artistDetail:ArtistDetailResponse, playListDetailList :List<TracksItem>, navController: NavController, musicViewModel: MusicPlayerViewModel) {
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            item{
                Column (
                    modifier= Modifier.background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xff3B13B0),
                                Color(0xff121212)

                            ),
                            start = Offset(0.0f, 50.0f),
                            end= Offset(0.0f, 800.0f)

                        )
                    )
                        ){
                    Box {

                        LoadArtistImageFromInternet(artistDetail?.images?.get(0)?.url.toString())
                        Box(

                        ) {
                            Box( modifier= Modifier.size(60.dp).padding(start = 16.dp, top = 16.dp).clip(CircleShape)
                                .background(Color(0xFF2B2727)).clickable {
                                    navController.popBackStack()
                                },){}
                              Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(55.dp)
                                    .padding(start = 20.dp, top = 20.dp),
                                tint = Color.White

                            )



                        }
                    }
                    Column() {
                        Text(name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 2.dp)
                            .clickable {
                                Log.d(
                                    "imagessssssssssssssss",
                                    artistDetail?.images?.get(0)?.url.toString()
                                )
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
                            Row (){
                                Image(
                                    painter = painterResource(id = R.drawable.shuffle),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(20.dp)
                                )
                                Spacer(modifier= Modifier.width(10.dp))
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
                }
            }
            items(playListDetailList.size) {
                val lyricsString= mutableListOf<String>()

                playListDetailList[it].artists?.forEach { it
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
                            serviceIntent?.apply {
                                putExtra(
                                    "musicLink",
                                    playListDetailList[it].previewUrl
                                )
                                putExtra(
                                    "musicName",
                                    playListDetailList[it].name!!
                                )
                                putExtra(
                                    "musicImage",
                                    playListDetailList[it].album?.images?.get(0)?.url!!
                                )
                                putExtra(
                                    "musicArtist",
                                    playListDetailList[it].artists?.get(0)?.name!!
                                )
                                putExtra("IsPlaying", musicViewModel.IsPlaying)
                            }

                            Log.d("mmm", musicViewModel.MusicName)
                            if (musicViewModel.MusicName != "Ajj ke baad") {
                                musicViewModel.IsMusic = true;
                            }

                            musicViewModel.MusicName = playListDetailList[it].name!!
                            musicViewModel.MusicImage =
                                playListDetailList[it].album?.images?.get(0)?.url!!
                            musicViewModel.MusicArtist = stringResult

                            startProgressUpdates(musicViewModel.IsMusic)

                            movieLink = URLEncoder.encode(playListDetailList[it].previewUrl)
                            movieName = URLEncoder.encode(playListDetailList[it].name!!)
                            movieImage =
                                URLEncoder.encode(playListDetailList[it].album?.images?.get(0)?.url!!)
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

                            LoadImageFromPlayListDetail(imageUrl = playListDetailList[it].album?.images?.get(0)?.url!!)
                            Column {
                                Text(
                                    text = playListDetailList[it].name!!,
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