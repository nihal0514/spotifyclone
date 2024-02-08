package com.example.spotifyclone.viewModel.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.spotifyclone.R


@Composable
fun LoadPlayBoxImage(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            placeholder(R.drawable.demoimg)
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .height(60.dp)
            .width(60.dp)
            .padding(8.dp)
    )
}

@Composable
fun LoadImageFromInternet(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            placeholder(R.drawable.demoimg)
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .height(150.dp)
            .width(150.dp) ,
        contentScale = ContentScale.FillBounds,
    )
}
@Composable
fun LoadArtistImageFromInternetUI(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            placeholder(R.drawable.demoimg)
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(140.dp)
            .clip(CircleShape),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun LoadArtistImageFromInternet(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            placeholder(R.drawable.demoimg)
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth() ,
        contentScale = ContentScale.FillWidth

    )
}

@Composable
fun LoadImageFromPlayListDetail(imageUrl: String) {
    val painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            placeholder(R.drawable.demoimg)
        }
    )

    Image(

        painter = painter,

        contentDescription = null,

        modifier = Modifier
            .height(40.dp)
            .width(50.dp)
    )

}