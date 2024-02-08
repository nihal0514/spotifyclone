package com.example.spotifyclone.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spotifyclone.screens.home.HomePage
//import com.example.spotifyclone.screens.home.ProvideMusicViewModel
import com.example.spotifyclone.screens.player.player
import com.example.spotifyclone.screens.setting.SettingsPage
import com.example.spotifyclone.viewModel.CategoryViewModel
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import java.net.URLDecoder

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val musicViewModel = hiltViewModel<MusicPlayerViewModel>()
    // ProvideMusicViewModel { musicViewModel ->

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }

        composable("main_screen") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                HomePage(navController, musicViewModel)
            }
        }

        composable("music_player_screen/{musicName}/{musicImage}/{musicLink}") {
            val musicName = it.arguments?.getString("musicName") ?: ""
            val movieString = URLDecoder.decode(musicName, "UTF-8")
            val musicLink = it.arguments?.getString("musicLink") ?: ""
            val musicImage = it.arguments?.getString("musicImage") ?: ""
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                player(navController, movieString, musicImage, musicLink, musicViewModel)
            }
        }
        composable("settings") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                SettingsPage(navController)

            }

        }

        //     }
    }
}
