package com.example.spotifyclone.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import com.example.spotifyclone.R
import com.example.spotifyclone.model.BottomNavigationItem
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.spotifyclone.navigation.artist.ArtistDetailScreen
import com.example.spotifyclone.screens.detail.DetailScreen
import com.example.spotifyclone.screens.library.LibraryPage
import com.example.spotifyclone.screens.player.PlayBox
import com.example.spotifyclone.screens.player.mediaPlayerBinder
import com.example.spotifyclone.screens.setting.SettingsPage
import com.example.spotifyclone.search.SearchDetail
import com.example.spotifyclone.search.SearchPage
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(navController: NavController, musicViewModel: MusicPlayerViewModel) {

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = R.drawable.home_off,
            unselectedIcon = R.drawable.home_on,
            hasNews = false,
            routeName = "main_home"
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = R.drawable.search_off,
            unselectedIcon = R.drawable.search_on,
            hasNews = false,
            badgeCount = 45,
            routeName = "search_page"
        ),
        BottomNavigationItem(
            title = "Your Library",
            selectedIcon = R.drawable.library_off,
            unselectedIcon = R.drawable.library_on,
            hasNews = true,
            routeName = "library_page"
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val homeNavController = rememberNavController()
    var isPlayBoxVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = musicViewModel.serviceIntent, block ={
        isPlayBoxVisible = musicViewModel.serviceIntent != null
    } )
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {

        Scaffold(
            bottomBar = {
                Column {

                    Log.d("isPlayBoxVisible",isPlayBoxVisible.toString())
                    if(isPlayBoxVisible)
                        PlayBox(navController, musicViewModel)

                  /*  AnimatedVisibility(
                        visible = isPlayBoxVisible,
                        enter = fadeIn(
                            // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                            initialAlpha = 0.4f
                        ),
                        exit = fadeOut(
                            // Overwrites the default animation with tween
                            animationSpec = tween(durationMillis = 250)
                        )
                    ) {
                        PlayBox(navController, musicViewModel)
                    }
*/

                    NavigationBar(
                        containerColor = Color(0xFF121212),
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    homeNavController.navigate(item.routeName)
                                },
                               /* label = {
                                    Text(text = item.title, color = Color.White)
                                },*/
                                icon = {

                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = if (index == selectedItemIndex) {
                                            painterResource(id = item.unselectedIcon)
                                        } else {
                                            painterResource(id = item.selectedIcon)
                                        },
                                        contentDescription = null,
                                    )

                                }
                            )
                        }
                    }
                }


            }
        ) {

            NavHost(navController = homeNavController, startDestination = "main_home") {

                composable("search_page") {
                    SearchPage(homeNavController, musicViewModel)
                }
                composable("library_page") {
                    LibraryPage(homeNavController)
                }
                composable("main_home") {
                    mainHome(navController,homeNavController)
                }
                composable("search_detail_page") {
                    SearchDetail(homeNavController, musicViewModel)
                }

                composable("detail_home/{id}/{name}/{image}") {
                    val id = it.arguments?.getString("id") ?: ""
                    val name = it.arguments?.getString("name") ?: ""
                    val image = it.arguments?.getString("image") ?: ""
                    DetailScreen(id, name, image,homeNavController, musicViewModel)
                }

                composable("artist_home/{id}/{name}") {
                    val id = it.arguments?.getString("id") ?: ""
                    val name = it.arguments?.getString("name") ?: ""
                    ArtistDetailScreen(id, name, homeNavController, musicViewModel)
                }



            }

        }
    }
}






