package com.example.spotifyclone.screens.player

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.screens.detail.movieImage
import com.example.spotifyclone.screens.detail.movieLink
import com.example.spotifyclone.screens.detail.movieName
import com.example.spotifyclone.viewModel.utils.LoadPlayBoxImage
import com.example.spotifyclone.service.MusicService
import com.example.spotifyclone.viewModel.MusicPlayerViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayBox(navController: NavController,musicViewModel: MusicPlayerViewModel) {
   // val musicViewModel = LocalMusicViewModel.current
    val context= LocalContext.current

    val interactionSource = remember { MutableInteractionSource() }
    musicViewModel.serviceIntent = remember { Intent(context, MusicService::class.java) }

        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip( RoundedCornerShape(6.dp))
                .background(Color.Gray,
                    //shape = RoundedCornerShape(8.dp)
                     )

                .clickable {
                    navController.navigate("music_player_screen/$movieName/$movieImage/$movieLink")
                },
        ) {
            Row(

                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                LoadPlayBoxImage(musicViewModel.MusicImage)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(  modifier = Modifier.padding(horizontal = 5.dp).width(220.dp),) {
                        Text(

                            text = musicViewModel.MusicName,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp).width(250.dp).basicMarquee(),
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 1,

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text= musicViewModel.MusicArtist,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp).width(250.dp).basicMarquee(),
                            fontSize = 10.sp,
                            color = Color.White,
                                    maxLines = 1,

                        )

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.device),
                            contentDescription = null,

                            modifier = Modifier
                                .height(20.dp)
                                .width(25.dp)

                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        val drawablePauseRes= R.drawable.property_1_pausebox
                        val drawableResumeRes= R.drawable.property_1_playbox
                        Image(
                            painter = if(mediaPlayerBinder?.IsPlayingProgress() == true){painterResource(id = drawablePauseRes)}else{painterResource(id = drawableResumeRes)},

                            contentDescription = null,

                            modifier = Modifier
                                .height(20.dp)
                                .width(25.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = {
                                        Log.d(
                                            "kposition",
                                            mediaPlayerBinder
                                                ?.IsPlayingProgress()
                                                .toString()
                                        )
                                        if (mediaPlayerBinder?.IsPlayingProgress() == true) {
                                            Log.d(
                                                "kpositionn",
                                                mediaPlayerBinder
                                                    ?.IsPlayingProgress()
                                                    .toString()
                                            )
                                            musicViewModel.serviceIntent!!.action = "PAUSE_MUSIC"
                                            context.startService(musicViewModel.serviceIntent)
                                        } else {
                                            Log.d(
                                                "kpositionn",
                                                mediaPlayerBinder
                                                    ?.IsPlayingProgress()
                                                    .toString()
                                            )
                                            musicViewModel.serviceIntent!!.action = "RESUME_MUSIC"
                                            context.startService(musicViewModel.serviceIntent)
                                        }
                                    }
                                )


                        )
                    }
                }
            }
        }

    }