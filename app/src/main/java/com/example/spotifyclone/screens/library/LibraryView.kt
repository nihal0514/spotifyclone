package com.example.spotifyclone.screens.library

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.spotifyclone.R
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.viewModel.utils.LoadImageFromInternet
import com.example.spotifyclone.viewModel.utils.LoadLibraryListImage
import com.example.spotifyclone.viewModel.utils.LoadPlayBoxImage
import java.net.URLEncoder

@Composable
fun libraryList(homeNavController: NavController,context: Context, playlistItems: List<PlaylistItems>) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            items(playlistItems.size) {
                val encodedImageUrl =
                    URLEncoder.encode(playlistItems[it]?.images?.get(0)?.url.toString())
                Box(

                    modifier = Modifier
                        .clickable {
                            homeNavController.navigate("detail_home/${playlistItems[it]?.id}/${playlistItems[it]?.name}/${encodedImageUrl}")

                        },
                ) {

                    Row(

                        Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LoadLibraryListImage(playlistItems[it]?.images?.get(0)?.url.toString())

                        Text(

                            text = playlistItems[it].name!!,
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontSize = 14.sp,

                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun libraryGrid(homeNavController: NavController,context: Context, playlistItems: List<PlaylistItems>) {


    LazyVerticalGrid(

        columns = GridCells.Fixed(2),

        ) {

        items(playlistItems.size) {
            val encodedImageUrl = URLEncoder.encode(playlistItems[it]?.images?.get(0)?.url.toString())
            Column (
                modifier = Modifier.padding(10.dp).clickable {
                    homeNavController.navigate("detail_home/${playlistItems[it]?.id}/${playlistItems[it]?.name}/${encodedImageUrl}")

                },
            ){
                LoadImageFromInternet(imageUrl = playlistItems[it]?.images?.get(0)?.url.toString())
                Text(
                    text =playlistItems[it].name!!,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }

}