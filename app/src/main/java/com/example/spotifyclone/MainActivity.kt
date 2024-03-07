package com.example.spotifyclone
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.spotifyclone.navigation.Navigation
import com.example.spotifyclone.ui.theme.SpotifycloneTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        val sharedPreferences = getSharedPreferences("PlaybackState", Context.MODE_PRIVATE)
        val isPreferenceExists = sharedPreferences.contains("currentMusicLink")
        if(isPreferenceExists){
            MusicName ="Satranga (From &quot;ANIMAL&quot;)"
            MusicArtist = "Arijit Singh"
            MusicImage ="https://i.scdn.co/image/ab67616d0000b273021d7017f73387b008eab271"
            movieLink ="https%3A%2F%2Fp.scdn.co%2Fmp3-preview%2F7908c3512a17427dbb2747fda555aa84aedeef0d%3Fcid%3D5853bd990b854a4a8a364817b56aa5c4"


        }*/
        setContent {
            SpotifycloneTheme {
                Surface(
                    contentColor= Color(0xFF121212),
                    color = Color(0xFF121212),
                    modifier = Modifier.fillMaxSize(),
                ) {
                  //  val musicViewModel = LocalMusicViewModel.current
                    // Get context
               //     val context = LocalContext.current
                   // musicViewModel.serviceIntent = remember { Intent(context, MusicService::class.java) }

                    Navigation()
                    val openLibrary = intent?.getBooleanExtra("openLibrary", false) ?: false
                    MyApp(openLibrary)

                }
            }
        }

    }

    @Composable
    fun MyApp(openLibrary: Boolean) {
        if (openLibrary) {
            Navigation()
        } else {

        }
    }




    override fun onPause() {
        super.onPause()
        Log.d("yesssssp","yessssssssssss")
    }

    override fun onStop() {
        super.onStop()
        Log.d("yesssssSSSSSSSSSSSSS","yessssssssssss")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("yesssss","yessssssssssss")
    }

}

