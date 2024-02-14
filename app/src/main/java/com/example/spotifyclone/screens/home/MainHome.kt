package com.example.spotifyclone.screens.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
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
import com.example.spotifyclone.model.AlbumsItem
import com.example.spotifyclone.model.ArtistsItem
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.viewModel.CategoryViewModel
import com.example.spotifyclone.viewModel.PlayListViewModel


@Composable
fun mainHome(navController: NavController,homeNavController: NavController){
    val categoryViewModel= hiltViewModel<CategoryViewModel>()
    val playListViewModel= hiltViewModel<PlayListViewModel>()
    categoryViewModel.getPopularArtist()
    playListViewModel.getMyPlaylist()
    playListViewModel.getPopularPlaylist()
    playListViewModel.getPopularArtist()
    val albumList: List<AlbumsItem> = categoryViewModel.popularAlbumList;
    val myplaylistList: List<PlaylistItems> = playListViewModel.myPlaylist;
    val popularPlaylistList: List<PlaylistItems> = playListViewModel.popularPlayList
    val popularArtistList: List<ArtistsItem> = playListViewModel.popularArtistList;
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
               /* brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff3B13B0),
                        Color(0xff271363),
                        Color(0xff1B1235),
                        Color(0xff121212)
                    ),

                    )*/
                Color(0xff121212)
            )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Good evening",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                   /* Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            homeNavController.navigate("settings")

                        }
                    )*/
                    Image(

                        painter = painterResource(id = R.drawable.notification),

                        contentDescription = null,

                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)

                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(

                        painter = painterResource(id = R.drawable.recents),

                        contentDescription = null,

                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)

                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(

                        painter = painterResource(id = R.drawable.settings),

                        contentDescription = null,

                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .clickable {
                                navController.navigate("settings")
                            }

                    )

                }
            }
            homeGridView(homeNavController, LocalContext.current)
            Spacer(modifier = Modifier.height(20.dp))
            Text(

                "New Releases For You",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),

                )
            Spacer(modifier = Modifier.height(5.dp))
            myPlayListUi(
                navController = homeNavController,
                context = LocalContext.current,
                playlistItems = popularPlaylistList
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "My Playlist",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),

                )
            Spacer(modifier = Modifier.height(5.dp))
            myPlayListUi(homeNavController, LocalContext.current, myplaylistList)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Your favourite artists",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp),

                )
            Spacer(modifier = Modifier.height(10.dp))
            ArtistUI(
                navController = homeNavController,
                context = LocalContext.current,
                popularArtistList
            )
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}