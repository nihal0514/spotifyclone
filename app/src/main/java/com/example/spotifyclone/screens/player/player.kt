package com.example.spotifyclone.screens.player

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.LocalImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.spotifyclone.R
import com.example.spotifyclone.service.MusicService
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun player(
    navController: NavController,
    musicName: String,
    imageUrl: String,
    musicLink: String,
    musicPlayerViewModel: MusicPlayerViewModel
) {
    val interactionSource = remember { MutableInteractionSource() }
    var showSheet by remember { mutableStateOf(false) }

    val context = LocalContext.current

    /* Convert our Image Resource into a Bitmap */
    val bitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.demoimg)
    }

    var bgColor = remember {
        Color(0x00000000)
    }
    var sbgColor = remember {
        Color(0x00000000)
    }
    if(musicPlayerViewModel.palette?.dominantSwatch != null){
        bgColor = Color(musicPlayerViewModel.palette?.dominantSwatch!!.rgb)
        sbgColor = Color(musicPlayerViewModel.palette?.dominantSwatch!!.bodyTextColor)
    }

  /*  var palette  = remember {

            Palette.from(bitmap).generate()

    }

    if (musicPlayerViewModel.albumImageBitmap != null) {
        palette = Palette.from(musicPlayerViewModel.albumImageBitmap!!).generate()
    }

    if(palette.dominantSwatch != null){
        bgColor = Color(palette.dominantSwatch!!.bodyTextColor)
    }
*/

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
                        bgColor,
                        Color.Black
                    ),
                )
            )

    ) {

        Column(

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 10.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            navController.popBackStack()
                        },
                    tint = Color.White,

                    )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    modifier = Modifier
                        .padding(vertical = 20.dp, horizontal = 10.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,

                            ) {
                            showSheet = true
                        },
                    tint = Color.White,

                    )

            }
            LoadImageFromInternetPlayer(musicPlayerViewModel.MusicImage, musicPlayerViewModel)

            AudioPlayer(musicName, musicLink, musicPlayerViewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AudioPlayer(musicName: String, musicLink: String, musicPlayerViewModel: MusicPlayerViewModel) {
    Log.d("lllll", musicPlayerViewModel.serviceIntent.toString())
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
                progressState =
                    mediaPlayerBinder?.mediaPlayerPostion() ?: 0 // Get the current position
                Log.d("mediaPlayerPostion", mediaPlayerBinder?.mediaPlayerPostion().toString())

                currentPosition =
                    mediaPlayerBinder?.mediaPlayerPostion() ?: 0 // Use default value if null
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = musicPlayerViewModel.MusicName,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                ),
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = musicPlayerViewModel.MusicArtist,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(450),
                    color = Color(0xB2FFFFFF),
                ),
                maxLines = 1,
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

    duration = mediaPlayerBinder?.mediaPlayerTotalDuration() ?: 0


    fun updateTextView() {
        val minutes = progressState / 1000 / 60
        val seconds = progressState / 1000 % 60

        // Format the duration as a string
        val currentPosition = String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)

        // Format the total duration
        val totalMinutes = duration / 1000 / 60
        val totalSeconds = duration / 1000 % 60
        val totalDuration =
            String.format(Locale.getDefault(), "%d:%02d", totalMinutes, totalSeconds)

        // Update the formatted duration string
        val parts = formattedDuration.split("/")
        currentDur = parts[0].trim()
        totalDur = parts[1].trim()
        formattedDuration = "$currentPosition / $totalDuration"
    }
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),

        factory = { context ->
            SeekBar(context).apply {
                max = 29000
                Log.d("pppppppppppppp", duration.toString())
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
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
            if (it.progress == 29000) {
                mediaPlayerBinder?.mediaPlayerSeekToProgress(0)
                progressState = 0;
            }
            updateTextView()
            Log.d("kjninknik", "")
        }

    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(currentDur, fontWeight = FontWeight.Normal, color = Color.White)
        Text(totalDur, fontWeight = FontWeight.Normal, color = Color.White)

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
            modifier = Modifier.width(200.dp),
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
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
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
fun LoadImageFromInternetPlayer(imageUrl: String, musicPlayerViewModel: MusicPlayerViewModel) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.demoimg),
    )

   /* if (painter.state is AsyncImagePainter.State.Success) {
        val drawable = (painter.state as AsyncImagePainter.State.Success).result.drawable

        val bitmap = if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565

            Bitmap.createBitmap(width, height, config).apply {
                val canvas = Canvas(this)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }

        // Now you have the bitmap, you can use it as needed
    }*/

//    val loader = ImageLoader(LocalContext.current)
//    val req = ImageRequest.Builder(LocalContext.current)
//        .data("https://images.dog.ceo/breeds/saluki/n02091831_3400.jpg") // demo link
//        .target { result ->
//            val bitmap = (result as BitmapDrawable).bitmap
//            musicPlayerViewModel.albumImageBitmap= bitmap
//            print(bitmap)
//        }
//        .build()

//    val disposable = loader.enqueue(req)


    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .padding(30.dp)
    )
}

var mediaPlayerBinder: MusicService.MediaPlayerBinder? by mutableStateOf(null)


class MediaPlayerConnection() : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        mediaPlayerBinder = service as MusicService.MediaPlayerBinder
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }

}