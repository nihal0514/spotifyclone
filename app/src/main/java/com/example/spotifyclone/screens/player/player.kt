package com.example.spotifyclone.screens.player

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.spotifyclone.R
import com.example.spotifyclone.service.MusicService
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun player(navController: NavController,musicName: String,musicImage : String,musicLink : String,musicPlayerViewModel: MusicPlayerViewModel){
    val interactionSource = remember { MutableInteractionSource() }
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        BottomSheet(musicPlayerViewModel) {
            showSheet = false
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff4D4D4D),
                        Color(0xff4D4D4D),
                    ),
                )
            )

    ){

        Column(

        ) {
            Row (
                modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                    ){
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp).clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ){
                        navController.popBackStack()
                    },
                    tint = Color.White,

                )
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                        ){
                    Text(
                        text = "PLAYING FROM YOUR LIBRARY",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontWeight = FontWeight(450),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.54.sp,
                        )
                    )
                    Text(
                        text = "Liked Songs",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp).clickable(
                        interactionSource = interactionSource,
                        indication = null,

                        ) {
                        showSheet= true
                    },
                    tint = Color.White,

                )

            }
            LoadImageFromInternetPlayer(musicPlayerViewModel.MusicImage)

            AudioPlayer(musicName,musicLink,musicPlayerViewModel)
        }
    }
}
@Composable
fun AudioPlayer(musicName: String,musicLink : String,musicPlayerViewModel: MusicPlayerViewModel) {
    Log.d("lllll",musicPlayerViewModel.serviceIntent.toString())
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    var currentPosition by remember { mutableStateOf(0) }


    var duration by remember { mutableStateOf(0) }
    var newDuration by remember { mutableStateOf(0) }
    var progressState by remember { mutableStateOf(0) }


    DisposableEffect(Unit) {

      //  startProgressUpdates()
        val handler = Handler(Looper.getMainLooper()) // Handler to update seek bar
        val updateSeekBar = object : Runnable {
            override fun run() {
//                currentPosition = mediaPlayerBinder?.mediaPlayerPostion() ?: 0 // Use default value if null
                progressState =  mediaPlayerBinder?.mediaPlayerPostion() ?: 0 // Get the current position
                Log.d("mediaPlayerPostion", mediaPlayerBinder?.mediaPlayerPostion().toString())

                currentPosition = mediaPlayerBinder?.mediaPlayerPostion() ?: 0 // Use default value if null
//                progressState =  mediaPlayerBinder?.mediaPlayerPostion() ?: 0 // Get the current position


                handler.postDelayed(this, 100) // Update seek bar every 100 milliseconds
            }
        }
        handler.postDelayed(updateSeekBar, 0) // Initial update
        onDispose {
            handler.removeCallbacks(updateSeekBar)
        }
    }
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .padding(start= 30.dp,end= 30.dp, top = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ){
        Column {
            Text(

                text = musicPlayerViewModel.MusicName,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                )
            )
            Text(
                text = "Black Sherif",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(450),
                    color = Color(0xB2FFFFFF),
                )
            )

        }
        Image(
            // on below line we are specifying the drawable image for our image.
            painter = painterResource(id = R.drawable.liked),

            // on below line we are specifying
            // content description for our image
            contentDescription = null,

            // on below line we are setting height
            // and width for our image.
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)

        )
    }
    var formattedDuration by remember { mutableStateOf("0:00 / 0:00") }
    var currentDur by remember { mutableStateOf("0.00") }
    var totalDur by remember { mutableStateOf("0.00") }

    duration= mediaPlayerBinder?.mediaPlayerTotalDuration() ?: 0


    fun updateTextView() {
        val minutes = progressState / 1000 / 60
        val seconds = progressState / 1000 % 60

        // Format the duration as a string
        val currentPosition = String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)

        // Format the total duration
        val totalMinutes = duration / 1000 / 60
        val totalSeconds = duration / 1000 % 60
        val totalDuration = String.format(Locale.getDefault(), "%d:%02d", totalMinutes, totalSeconds)

        // Update the formatted duration string
        val parts = formattedDuration.split("/")
        currentDur = parts[0].trim()
        totalDur= parts[1].trim()
        formattedDuration = "$currentPosition / $totalDuration"
    }
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp,end= 16.dp),

        factory = { context ->
            SeekBar(context).apply {
                max = 29000
                Log.d("pppppppppppppp", duration.toString())
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if (fromUser) {
                        //    mediaPlayer.seekTo(progress)
                            mediaPlayerBinder?.mediaPlayerSeekToProgress(progress)
                            progressState = progress
                        }
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
            }
        },
        update = {
            it.progress = progressState
            if(it.progress== 29000){
                mediaPlayerBinder?.mediaPlayerSeekToProgress(0)
                progressState= 0;
            }
            updateTextView()
            Log.d("kjninknik", "")
        }

    )

    Row(
        modifier= Modifier.fillMaxWidth().padding(start = 30.dp,end= 30.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(currentDur, fontWeight = FontWeight.Normal, color = Color.White)
        Text(totalDur, fontWeight = FontWeight.Normal, color = Color.White)

    }

    Row(
        modifier= Modifier.fillMaxWidth().padding(start= 30.dp,end= 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            // on below line we are specifying the drawable image for our image.
            painter = painterResource(id = R.drawable.shuffle),

            // on below line we are specifying
            // content description for our image
            contentDescription = null,

            // on below line we are setting height
            // and width for our image.
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)

        )

        Row(
            modifier= Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                // on below line we are specifying the drawable image for our image.
                painter = painterResource(id = R.drawable.previous),

                // on below line we are specifying
                // content description for our image
                contentDescription = null,

                // on below line we are setting height
                // and width for our image.
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)

            )
            val drawableRes = if (mediaPlayerBinder!!.IsPlayingProgress()) {
                R.drawable.pause_button
            } else {
                R.drawable.property_1_play
            }

            Image(
                // on below line we are specifying the drawable image for our image.
                painter = painterResource(id = drawableRes),

                // on below line we are specifying
                // content description for our image
                contentDescription = null,

                // on below line we are setting height
                // and width for our image.
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clickable(interactionSource = interactionSource,
                        indication = null,){
                        //IsPlaying = !IsPlaying
                  /*      if (musicPlayerViewModel.IsPlaying) {
                          //  mediaPlayer.start()

//                            serviceIntent.action = "PLAY"
//                            context.startService(serviceIntent)
//
                            musicPlayerViewModel.serviceIntent!!.action = "PAUSE_MUSIC"
                            context.startService(musicPlayerViewModel.serviceIntent)
//                            serviceIntent.action = "RESUME_MUSIC"
//                            context.startService(serviceIntent)

                            //   updateProgress.s
                        } else {
                         //   mediaPlayer.pause()
//                            serviceIntent.action= "PAUSE"
//                            serviceIntent.action = "PAUSE_MUSIC"
//                            context.startService(serviceIntent)


                            musicPlayerViewModel.serviceIntent!!.action = "RESUME_MUSIC"
                            context.startService(musicPlayerViewModel.serviceIntent)
                     //       context.startService(serviceIntent)
//                            context.stopService(serviceIntent)
                         //   viewModelScope.cancel()
                        }*/
                        if (mediaPlayerBinder!!.IsPlayingProgress()) {
                            //  mediaPlayer.start()

//                            serviceIntent.action = "PLAY"
//                            context.startService(serviceIntent)
//
                            musicPlayerViewModel.serviceIntent!!.action = "PAUSE_MUSIC"
                            context.startService(musicPlayerViewModel.serviceIntent)
//                            serviceIntent.action = "RESUME_MUSIC"
//                            context.startService(serviceIntent)

                            //   updateProgress.s
                        } else {
                            //   mediaPlayer.pause()
//                            serviceIntent.action= "PAUSE"
//                            serviceIntent.action = "PAUSE_MUSIC"
//                            context.startService(serviceIntent)


                            musicPlayerViewModel.serviceIntent!!.action = "RESUME_MUSIC"
                            context.startService(musicPlayerViewModel.serviceIntent)
                            //       context.startService(serviceIntent)
//                            context.stopService(serviceIntent)
                            //   viewModelScope.cancel()
                        }

                    }

            )
            Image(
                // on below line we are specifying the drawable image for our image.
                painter = painterResource(id = R.drawable.next),

                // on below line we are specifying
                // content description for our image
                contentDescription = null,

                // on below line we are setting height
                // and width for our image.
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)

            )
        }

        Image(
            // on below line we are specifying the drawable image for our image.
            painter = painterResource(id = R.drawable.repeat),

            // on below line we are specifying
            // content description for our image
            contentDescription = null,

            // on below line we are setting height
            // and width for our image.
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)

        )
    }

}




@Composable
fun LoadImageFromInternetPlayer(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true) // Enable a crossfade animation when the image loads
            placeholder(R.drawable.demoimg) // Placeholder image while loading
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .padding(30.dp)

        // You can also add other modifiers or specify the size of the Image
    )
}
var mediaPlayerBinder: MusicService.MediaPlayerBinder? by mutableStateOf(null)



class MediaPlayerConnection(): ServiceConnection{
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        mediaPlayerBinder = service as MusicService.MediaPlayerBinder
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }

}