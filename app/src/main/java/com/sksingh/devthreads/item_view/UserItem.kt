package com.sksingh.devthreads.item_view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

import com.sksingh.devthreads.models.UserModel
import com.sksingh.devthreads.navigations.Routes
import com.sksingh.devthreads.ui.theme.myfont2

@Composable
fun UserItem(users:UserModel,
             navHostController: NavHostController){



        Column(modifier = Modifier
                .background(Color.Black.copy(alpha = 1f),
//                        shape = RoundedCornerShape(26.dp)
                ),
                ) {
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
                        .clickable {
                                val routes = Routes.OtherUser.routes.replace("{data}",users.uid!!)
                                navHostController.navigate(routes)

                        },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        Image(painter = rememberAsyncImagePainter(model = users.imageUrl),
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                        .size(55.dp)
                                        .clip(CircleShape)
                                        .border(
                                                BorderStroke(1.dp, Color.White),
                                                shape = CircleShape
                                        )
                                        .clickable {

                                        }

                        )
                        Column {

                                Text(text = users.username,
                                        modifier = Modifier
                                                .padding(start = 10.dp),
                                        fontSize = 20.sp,
                                        color = Color.White,
                        fontFamily = myfont2
                                )
                                Text(text =users.name,
                                        modifier = Modifier
                                                .padding(start = 10.dp),
                                        fontSize = 20.sp,
                                        color = Color.White,
                        fontFamily = myfont2
                                )
                        }
                }



                Spacer(modifier = Modifier
                        .padding(5.dp))
        }



}


