package com.example.spotifyclone.screens.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.example.spotifyclone.R
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import com.example.spotifyclone.viewModel.utils.LoadPlayBoxImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(musicPlayerViewModel: MusicPlayerViewModel, onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        containerColor = Color.Black,
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {

        Column(
        ) {
            Row(

                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                LoadPlayBoxImage(musicPlayerViewModel.MusicImage)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(220.dp)
                    ) {
                        Text(

                            text = musicPlayerViewModel.MusicName,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = musicPlayerViewModel.MusicArtist,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontSize = 10.sp,
                            color = Color.White
                        )

                    }

                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = Color.Gray
            )
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.liked)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Like", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.add_to_playlist)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Add to Playlist", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.add_to_queue)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Add to Queue", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.view_album)
                Spacer(modifier = Modifier.width(10.dp))
                Text("View Album", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                    modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
                    ) {
                PlayerSvgImage(R.drawable.view_artists)
                Spacer(modifier = Modifier.width(10.dp))
                Text("View Artists", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.share)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Share", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.go_to_song_radio)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Go to Song Radio", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Row(
                modifier= Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                PlayerSvgImage(R.drawable.show_credits)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Show Credits", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.W100)
            }
            Spacer(modifier = Modifier.height(10.dp))


        }
    }
}
@Composable
fun PlayerSvgImage( image:Int) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()


    Image(
        painter = rememberAsyncImagePainter(image, imageLoader),
        contentDescription = null,
        modifier = Modifier
            .height(18.dp)
            .width(20.dp)
    )
}