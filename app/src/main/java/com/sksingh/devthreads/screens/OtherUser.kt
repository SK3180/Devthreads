package com.sksingh.devthreads.screens

import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.sksingh.devthreads.item_view.PostItem
import com.sksingh.devthreads.models.UserModel
import com.sksingh.devthreads.navigations.Routes
import com.sksingh.devthreads.utils.SharedPref
import com.sksingh.devthreads.viewmodel.AuthViewModel
import com.sksingh.devthreads.viewmodel.UserViewModel



@Composable
fun OtherUser(navHostController: NavHostController,uid:String){
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val users by userViewModel.users.observeAsState(null)

    val followersList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)



    userViewModel.fetchThreads(uid)
    userViewModel.fetchUser(uid)
    userViewModel.getFollower(uid)
    userViewModel.getFollowing(uid)

    var currentUserId = ""
        if (FirebaseAuth.getInstance().currentUser != null){ currentUserId = FirebaseAuth.getInstance().currentUser!!.uid }




    LaunchedEffect(firebaseUser){
//        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser==null){

            navHostController.navigate(Routes.Login.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }

    LazyColumn(Modifier.background(color = Color.Black)) {
        item {

            Column(modifier = Modifier
                .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally) {


                Box( modifier = Modifier.height(190.dp)) {

                    Image(painter = rememberAsyncImagePainter(model = users!!.imageUrl),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentDescription = "Banner",
                        contentScale = ContentScale.Crop )
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter) {
                        Image(painter = rememberAsyncImagePainter(model = users!!.imageUrl),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(95.dp)
                                .clip(CircleShape)
                                .border(
                                    BorderStroke(6.dp, Color.White),
                                    shape = CircleShape
                                ))
                    }

                    Spacer(modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth())
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        val textcolor = Color.White
                        Text(text = users!!.name,
                            color = textcolor,
                            fontSize = 35.sp,
                            fontFamily = FontFamily.SansSerif )
                        Text(text = users!!.username,
                            color = textcolor, fontSize = 20.sp )
                        Text(text = users!!.bio,
                            color = textcolor,
                            modifier = Modifier.padding(horizontal = 20.dp),
                            maxLines = 3 )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Card(modifier = Modifier.padding(horizontal = 20.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically // Adjusted alignment
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = "1",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold)
                                Text(text = "Posts",
                                    fontSize = 20.sp,
                                    color = Color.Black)
                            }

                            Column(
                                modifier = Modifier.padding(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "${followersList?.size}",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold)
                                Text(text = "Followers",
                                    fontSize = 20.sp,
                                    color = Color.Black) // Changed to "Followers"
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding()
                            ) {
                                Text(text = "${followingList?.size}",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold)
                                Text(text = "Following",
                                    color = Color.Black,
                                    fontSize = 20.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Card(modifier = Modifier
                        .width(110.dp)
                        .clickable {
                            if (FirebaseAuth.getInstance().currentUser!=null)
                                userViewModel.followUsers(uid, FirebaseAuth.getInstance().currentUser!!.uid)
                        }
                        .height(40.dp),
                        colors = CardDefaults.cardColors(Color.DarkGray)) {
                        Column(modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {


                            Text(
                                text =  if (followersList!= null && followersList!!.isNotEmpty() && followersList!!.contains(currentUserId))
                                    "Following"
                                else
                                "Follow",
                                fontSize = 20.sp,
                                color = Color.White)
                        }

                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Card(modifier = Modifier
                        .width(110.dp)
                        .height(40.dp),colors = CardDefaults.cardColors(Color.DarkGray)) {
                        Column(modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(modifier = Modifier.clickable {


                            },
                                text = "Report",
                                fontSize = 20.sp,
                                color = Color.White)
                        }


                    }

                }
                Divider(modifier = Modifier.padding(10.dp), thickness = 0.5.dp, color = Color.Gray)
                Text(
                    text = "Posts",Modifier,
                    fontSize = 20.sp,
                    color = Color.White)


            }

        }
        if (threads != null && users!= null) {
            items(threads ?: emptyList()) { pair ->
                PostItem(
                    thread = pair,
                    users = users!!,
                    navHostController = navHostController,
                    userId = FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }
    }
}







