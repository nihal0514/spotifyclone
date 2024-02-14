package com.example.spotifyclone.search

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.ItemsItemsSearch
import com.example.spotifyclone.model.searchGenreModel
import com.example.spotifyclone.screens.detail.movieImage
import com.example.spotifyclone.screens.detail.movieLink
import com.example.spotifyclone.screens.detail.movieName
import com.example.spotifyclone.screens.player.MediaPlayerConnection
import com.example.spotifyclone.viewModel.utils.LoadImageFromPlayListDetail
import com.example.spotifyclone.viewModel.MusicPlayerViewModel
import com.example.spotifyclone.viewModel.SearchViewModel
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage(homeNavController: NavController,musicPlayerViewModel: MusicPlayerViewModel){

    var offset by remember { mutableStateOf(0f) }
 //   var isSearch by remember { mutableStateOf(false) }
    var textState by remember { mutableStateOf(TextFieldValue()) }

    val searchListViewModel= hiltViewModel<SearchViewModel>()
    val searchItemsList: List<ItemsItemsSearch> = searchListViewModel.searchResultList

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
          color =  Color(0xff121212)
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

        ){
            Text("Search", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ), color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),)
            Box (
                modifier= Modifier.fillMaxWidth().background(color = Color.White).clickable {
                    homeNavController.navigate("search_detail_page")
                }
                    ){

                Row(
                    modifier= Modifier.fillMaxWidth().height(40.dp).padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        "Artist, songs or podcasts",
                        fontSize = 12.sp,
                        color= Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
               /* TextField(
                    value = textState,

                    onValueChange = {
                      *//*  textState = it
                        Log.d("textState", textState.text)
                        searchListViewModel.getSearchResult(textState.text)
                        Log.d("llllllllllllllllll", searchItemsList.toString())

                        *//**//*   if(!textState.text.isEmpty()){
                               isSearch= true
                           }else{
                               isSearch= false
                           }*//*
                    },
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                "Artist, songs or podcasts",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.clickable {
                                    homeNavController.navigate("search_detail_page")
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)


                )*/
            }
          //  if(!isSearch){
                defaultSearchView(LocalContext.current)
         //   }else{
         //       searchItemList(context = LocalContext.current, playListDetailList =searchItemsList,musicPlayerViewModel)
      //      }


        }
    }
}

@Composable
fun defaultSearchView(context: Context) {
    Column(
    ) {
        Text("Browse All", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),)
        topGenreGridview(context)
    }


}


@Composable
fun searchItemList(context: Context, playListDetailList :List<ItemsItemsSearch>,musicPlayerViewModel: MusicPlayerViewModel) {

    val serviceIntent = musicPlayerViewModel.serviceIntent
    fun startProgressUpdates(isMusic: Boolean) {

        if(isMusic){
            serviceIntent?.action = "STOP_MUSIC"
            context.startService(serviceIntent)
        }

        serviceIntent?.action = "PLAY_MUSIC"
        context.startService(serviceIntent)
        if (serviceIntent != null) {
            context.bindService(serviceIntent, MediaPlayerConnection(),Context.BIND_AUTO_CREATE)
        }

    }

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            items(playListDetailList.size) {
                Box(
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp,
                            //  vertical = 5.dp
                        )
                        .background(Color(0xff121212))
                        .clickable {
                            serviceIntent?.apply {
                                putExtra(
                                    "musicLink",
                                    playListDetailList[it].previewUrl
                                )
                                putExtra(
                                    "musicName",
                                    playListDetailList[it].name!!
                                )
                                putExtra(
                                    "musicImage",
                                    playListDetailList[it].album?.images?.get(0)?.url!!
                                )
                                putExtra(
                                    "musicArtist",
                                    playListDetailList[it].artists?.get(0)?.name!!
                                )
                                putExtra("IsPlaying", musicPlayerViewModel.IsPlaying)
                            }

                            Log.d("mmm", musicPlayerViewModel.MusicName)
                            if (musicPlayerViewModel.MusicName != "Ajj ke baad") {
                                musicPlayerViewModel.IsMusic = true;
                            }

                            musicPlayerViewModel.MusicName = playListDetailList[it].name!!
                            musicPlayerViewModel.MusicImage =
                                playListDetailList[it].album?.images?.get(0)?.url!!
                            musicPlayerViewModel.MusicArtist =
                                playListDetailList[it].artists?.get(0)?.name!!

                            startProgressUpdates(musicPlayerViewModel.IsMusic)

                            movieLink = URLEncoder.encode(playListDetailList[it].previewUrl)
                            movieName = URLEncoder.encode(playListDetailList[it].name!!)
                            movieImage =
                                URLEncoder.encode(playListDetailList[it].album?.images?.get(0)?.url!!)
                        },
                ) {

                    Row(

                        Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        val lyricsString= mutableListOf<String>()

                        playListDetailList[it].artists?.forEach { it
                            lyricsString.add(it?.name.toString())
                        }
                        val stringResult = lyricsString.joinToString(",")


                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){

                            LoadImageFromPlayListDetail(imageUrl = playListDetailList[it].album?.images?.get(0)?.url!!)
                            Column {
                                Text(
                                    text = playListDetailList[it].name!!,
                                    fontWeight = FontWeight.Bold,

                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .width(250.dp),
                                    fontSize = 14.sp,

                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Row(
                                    modifier = Modifier.padding(horizontal = 5.dp)

                                ){
                                    Box(
                                        modifier= Modifier
                                            .background(
                                                color = Color.Gray
                                            )
                                            .width(35.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "LYRICS",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            color = Color.Black,
                                        )

                                    }
                                    Text(
                                        text = stringResult,
                                        fontWeight = FontWeight.Bold,

                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .width(215.dp),
                                        fontSize = 10.sp,

                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }
                            }
                        }
                        Row (
                            modifier= Modifier.padding(end = 20.dp)
                        ){
                            /*Image(

                                painter = painterResource(id = R.drawable.liked),

                                contentDescription = null,

                                modifier = Modifier
                                    .height(15.dp)
                                    .width(20.dp)
                            )*/
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(

                                painter = painterResource(id = R.drawable.more_option),

                                contentDescription = null,

                                modifier = Modifier
                                    .height(16.dp)
                                    .width(20.dp)

                            )
                        }


                    }
                }
            }
        }
    }
}
@Composable
fun topGenreGridview(context: Context) {

    lateinit var topGenreGridList: List<searchGenreModel>
    topGenreGridList = ArrayList<searchGenreModel>()

    topGenreGridList = topGenreGridList + searchGenreModel(R.drawable.pop)
    topGenreGridList = topGenreGridList + searchGenreModel( R.drawable.christian)
    topGenreGridList = topGenreGridList + searchGenreModel( R.drawable.indie)
    topGenreGridList = topGenreGridList + searchGenreModel( R.drawable.rock)
    topGenreGridList = topGenreGridList + searchGenreModel(R.drawable.podcast)
    topGenreGridList = topGenreGridList + searchGenreModel( R.drawable.madeforyou)

    LazyVerticalGrid(

        columns = GridCells.Fixed(2),


    ) {

        items(topGenreGridList.size) {

                Row(

                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .clickable {

                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = topGenreGridList[it].image),

                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,

                        modifier = Modifier
                            .height(100.dp)
                            .width(400.dp)

                    )

                }
            }
        }


}

