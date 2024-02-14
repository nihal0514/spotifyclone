package com.example.spotifyclone.screens.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.ArtistsItem
import com.example.spotifyclone.model.PlaylistItems
import com.example.spotifyclone.model.homeGridModel
import com.example.spotifyclone.viewModel.utils.LoadArtistImageFromInternetUI
import com.example.spotifyclone.viewModel.utils.LoadImageFromInternet

@Composable
fun homeGridView(navController: NavController, context: Context) {
    lateinit var homeGridList: List<homeGridModel>
    homeGridList = ArrayList<homeGridModel>()

    homeGridList = homeGridList + homeGridModel("Liked Songs", R.drawable.likedsong,"5WORkg8FwGGN6q56QzHod2")
    homeGridList = homeGridList + homeGridModel("Arijit Singh", R.drawable.arijits_photo,"37i9dQZF1DWYztMONFqfvX")
    homeGridList = homeGridList + homeGridModel("Emotional Songs", R.drawable.emotionalsong,"37i9dQZF1DXdFesNN9TzXT")
    homeGridList = homeGridList + homeGridModel("A Place We Knew", R.drawable.megahits,"37i9dQZF1DWVq1SXCH6uFn")

    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {

/*        var i = 0;
        while (i < homeGridList.size) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(

                    modifier = Modifier

                        .weight(1f)
                        .background(Color(0xff444473))


                ) {

                    Row(

                        Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Image(

                            painter = painterResource(id =homeGridList[i].languageImg),

                            contentDescription = null,

                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .clickable {

                                },

                        )

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(

                            text = homeGridList[i].languageName,
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
        *//*        Box(

                    modifier = Modifier
                        .padding(3.dp)
                        .weight(1f)
                        .background(Color(0xff444473))


                ) {

                    Row(

                        Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(

                            painter = painterResource(id =homeGridList[i+1].languageImg),

                            contentDescription = null,

                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(

                            text = homeGridList[i+1].languageName,
                            fontWeight = FontWeight.Bold,

                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontSize = 10.sp,

                            color = Color.White
                        )
                    }
                }*//*
               i+=2

            }
        }*/
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(

                modifier = Modifier
                    .weight(1f)
                    .clip( RoundedCornerShape(5.dp))
                    .background(Color(0xff2a2a2a))

            ) {
                Row(

                    Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("detail_home/${homeGridList[0].id}/${homeGridList[0].languageName}")
                        },
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Image(

                        painter = painterResource(id = homeGridList[0].languageImg),

                        contentDescription = null,
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .background(Color(0xff2a2a2a), shape = RoundedCornerShape(5.dp))
                            .clickable {

                            },


                        )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(

                        text = homeGridList[0].languageName,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 10.sp,
                        color = Color.White
                    )

                }

            }
            Spacer(modifier = Modifier.width(10.dp))
            Row(
                modifier = Modifier

                    .weight(1f)
                    .clip( RoundedCornerShape(5.dp))
                    .background(Color(0xff2a2a2a))

            ) {
                Row(

                    Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("detail_home/${homeGridList[1].id}/${homeGridList[1].languageName}")
                        },
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Image(

                        painter = painterResource(id = homeGridList[1].languageImg),

                        contentDescription = null,

                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)

                            .clickable {

                            },

                        )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(

                        text = homeGridList[1].languageName,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 10.sp,
                        color = Color.White
                    )

                }

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(

                modifier = Modifier
                    .clip( RoundedCornerShape(5.dp))
                    .weight(1f)
                    .background(Color(0xff2a2a2a))


            ) {
                Row(

                    Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("detail_home/${homeGridList[2].id}/${homeGridList[2].languageName}")
                        },
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Image(

                        painter = painterResource(id = homeGridList[2].languageImg),

                        contentDescription = null,

                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .clickable {

                            },

                        )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(

                        text = homeGridList[2].languageName,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 10.sp,
                        color = Color.White
                    )

                }

            }
            Spacer(modifier = Modifier.width(10.dp))
            Row(

                modifier = Modifier
                    .weight(1f)
                    .clip( RoundedCornerShape(5.dp))
                    .background(Color(0xff2a2a2a))


            ) {
                Row(

                    Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("detail_home/${homeGridList[3].id}/${homeGridList[3].languageName}")
                        },
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Image(

                        painter = painterResource(id = homeGridList[3].languageImg),

                        contentDescription = null,

                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .clickable {

                            },

                        )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(

                        text = homeGridList[3].languageName,
                        fontWeight = FontWeight.Bold,

                        modifier = Modifier.padding(horizontal = 5.dp),
                        fontSize = 10.sp,
                        color = Color.White
                    )

                }

            }
        }



    }
}

@Composable
fun myPlayListUi(navController: NavController, context: Context, playlistItems: List<PlaylistItems>) {

    LazyRow (
        modifier= Modifier.padding(vertical = 15.dp)
    ){
        items(playlistItems.size){
            Row{
                if(it==0)
                    Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("detail_home/${playlistItems[it]?.id}/${playlistItems[it]?.name}")
                        }
                ) {
                    LoadImageFromInternet(imageUrl = playlistItems[it]?.images?.get(0)?.url.toString())
                    Text(
                        text = playlistItems[it].name!!,
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))

            }

        }

    }
}

@Composable
fun ArtistUI(navController: NavController, context: Context, popularArtistList: List<ArtistsItem>) {

    LazyRow {
        items(popularArtistList.size){
            Row{
                if(it==0)
                    Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("artist_home/${popularArtistList[it]?.id}/${popularArtistList[it]?.name}")
                        }
                ) {
                    LoadArtistImageFromInternetUI(imageUrl = popularArtistList[it]?.images?.get(0)?.url.toString())
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = popularArtistList[it].name!!,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterHorizontally)
                        ,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }

        }

    }
}