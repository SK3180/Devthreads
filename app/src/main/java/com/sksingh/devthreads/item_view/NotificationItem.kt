package com.sksingh.devthreads.item_view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.sksingh.devthreads.R
import com.sksingh.devthreads.models.ThreadModel
import com.sksingh.devthreads.models.UserModel
import com.sksingh.devthreads.navigations.Routes
import com.sksingh.devthreads.ui.theme.myfont2
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun NotificationView(
        thread:ThreadModel,
             users:UserModel,
             navHostController: NavHostController,
             userId:String
){


        val context = LocalContext.current
        Column(modifier = Modifier
                .background(Color.Black.copy(alpha = 1f),
//                        shape = RoundedCornerShape(26.dp)
                ),

                ) {
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
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
                                                val routes = Routes.OtherUser.routes.replace("{data}",users.uid!!)
                                                navHostController.navigate(routes){
                                                        popUpTo(routes)
                                                        launchSingleTop = true
                                                }



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
                                Text(text = thread.timezone,
                                        modifier = Modifier
                                                .padding(start = 10.dp, top = 3.dp),
                                        fontSize = 13.sp,
                                        color = Color.White,
                                )
                        }
                }
                Column {
                        Text(text = thread.thread
                                                ,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                                start = 20.dp,
                                                top = 10.dp,
                                                end = 20.dp,
                                                bottom = 5.dp
                                        ),
                                fontSize = 20.sp,
                                color = Color.White,
//                        fontFamily = myfont2
                        )
                        if (thread.image != ""){

                                Card(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                                top = 10.dp,
                                                end = 20.dp,
                                                start = 20.dp,
                                                bottom = 10.dp
                                        )
                                        .height(250.dp),
                                        shape = RoundedCornerShape(16.dp)) {
                                        Image(modifier = Modifier
                                                .fillMaxWidth()
                                                .height(250.dp),
                                                painter = rememberAsyncImagePainter(model = thread.image),
                                                contentDescription = "photo",
                                                alignment = Alignment.Center,
                                                contentScale = ContentScale.Crop)


                                }
                        }

                        Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 10.dp, bottom = 10.dp)) {
                                var heartColor by remember { mutableStateOf(Color.White) }
                                var isScaled by remember { mutableStateOf(false) }
                                var saveColor by remember { mutableStateOf(Color.White) }
                                var saveScaled by remember { mutableStateOf(false) }

                                Image(
                                        modifier = Modifier
                                                .clickable {
                                                        heartColor =
                                                                if (heartColor == Color.White) Color.Red else Color.White
                                                        isScaled = true
                                                        // Set a delay to revert the scaling effect after a short duration
                                                        GlobalScope.launch {
                                                                delay(200) // Adjust the duration as needed
                                                                isScaled = false
                                                        }
                                                }
                                                .size(35.dp)
                                                .padding(start = 10.dp, top = 5.dp)
                                                .scale(if (isScaled) 1.4f else 1f), // Apply scaling effect when isScaled is true
                                        painter = painterResource(id = R.drawable.heart),
                                        contentDescription = "Like",
                                        colorFilter = ColorFilter.tint(heartColor) // Apply the color filter
                                )
                                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                                Image( modifier = Modifier
                                        .size(34.dp)
                                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                                        painter = painterResource(id = R.drawable.send),
                                        contentDescription = "share",
                                        colorFilter = ColorFilter.tint(color = Color.White)
                                )
                                Image( modifier = Modifier
                                        .clickable {

                                                if (saveColor == Color.White) {
                                                        Toast
                                                                .makeText(
                                                                        context,
                                                                        "Saved",
                                                                        Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                }
                                                saveColor =
                                                        if (saveColor == Color.White) {
                                                                Color.Green

                                                        } else Color.White
                                                saveScaled = true

                                                // Set a delay to revert the scaling effect after a short duration
                                                GlobalScope.launch {
                                                        delay(200) // Adjust the duration as needed
                                                        saveScaled = false
                                                }
                                        }
                                        .fillMaxWidth()
                                        .size(35.dp)
                                        .scale(if (saveScaled) 1.4f else 1f)
                                        .padding(end = 10.dp, top = 5.dp, bottom = 5.dp),

                                        alignment = Alignment.CenterEnd,
                                        painter = painterResource(id = R.drawable.save),
                                        contentDescription = "Like",
                                        colorFilter = ColorFilter.tint(saveColor))



                        }

                }

                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier
                        .padding(5.dp))
        }



}
