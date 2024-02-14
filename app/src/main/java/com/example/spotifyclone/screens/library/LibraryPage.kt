package com.example.spotifyclone.screens.library

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.model.homeGridModel
import com.example.spotifyclone.viewModel.utils.LoadImageFromInternet
import com.example.spotifyclone.viewModel.utils.LoadPlayBoxImage
import com.example.spotifyclone.viewModel.PlayListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryPage(homeNavController: NavController){
    lateinit var homeGridList: List<homeGridModel>
    homeGridList = ArrayList<homeGridModel>()

    val playListViewModel= hiltViewModel<PlayListViewModel>()
    playListViewModel.getMyPlaylist()
    val myplaylistList: List<PlaylistItems> = playListViewModel.myPlaylist
    var offset by remember { mutableStateOf(0f) }
    var textState by remember { mutableStateOf(TextFieldValue()) }

    var listGridChange by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(     color =  Color(0xff121212)
        )
        .padding(horizontal = 10.dp)
        .scrollable(
            orientation = Orientation.Vertical,

            state = rememberScrollableState { delta ->
                offset += delta
                delta
            }
        )
    ) {
        Column(

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Your Library", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Icon(
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp),
                                imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )

                }


            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    modifier = Modifier
                        .width(76.dp)
                        .padding(4.dp)
                        .background(
                            color = Color(0xFF2C2A2A),
                            shape = RoundedCornerShape(20.dp)
                        )
                     //   .border(1.dp, , RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "Playlists",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .width(70.dp)
                        .padding(4.dp)
                        .background(
                            color = Color(0xFF2C2A2A),
                            shape = RoundedCornerShape(20.dp)
                        )

                ) {
                    Text(
                        text = "Albums",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .width(94.dp)
                        .padding(4.dp)
                        .background(color = Color(0xFF2C2A2A),
                            shape = RoundedCornerShape(20.dp)
                        )

                ) {
                    Text(
                        text = "Downloaded",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(){
                    Image(

                        painter = painterResource(id = R.drawable.sort),

                        contentDescription = null,

                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Most Recent",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                }
                Image(
                    painter = if(listGridChange)painterResource(id = R.drawable.grid_view) else painterResource(id = R.drawable.list_view),

                    contentDescription = null,

                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .clickable {
                          listGridChange = !listGridChange
                        }

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if(listGridChange)
                libraryList(homeNavController,LocalContext.current,myplaylistList)
            else
                libraryGrid(homeNavController,LocalContext.current,myplaylistList)
        }
        }
}

