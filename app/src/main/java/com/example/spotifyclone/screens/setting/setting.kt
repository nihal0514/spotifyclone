package com.example.spotifyclone.screens.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spotifyclone.R
import com.example.spotifyclone.model.CurrentProfileresponse
import com.example.spotifyclone.viewModel.UserViewModel

@Composable
fun SettingsPage(navController: NavController) {

    val UserViewModel = hiltViewModel<UserViewModel>()
    UserViewModel.getCurrentUser()
    val userDetail: CurrentProfileresponse = UserViewModel.userDetail
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF121212))
            .padding(horizontal = 10.dp)

    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                            navController.popBackStack()
                    })
                Text(
                    "Settings",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("", color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(

                ) {
                    Image(
                        painter = painterResource(R.drawable.profilepic),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            userDetail.displayName.toString(),
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            "View Profile",
                            color = Color.Gray,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }


                }


            }

            Column() {
                Text(
                    "Premium plan",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "View your plan",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )


            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Account",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column() {
                Text("Email", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Text(
                    "nihaldwivedi@gmail.com",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }


            }

        // }
    }
}