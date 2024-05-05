package com.sksingh.devthreads.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.sksingh.devthreads.R
import com.sksingh.devthreads.item_view.PostItem
import com.sksingh.devthreads.models.UserModel
import com.sksingh.devthreads.utils.SharedPref
import com.sksingh.devthreads.viewmodel.AuthViewModel
import com.sksingh.devthreads.viewmodel.HomeViewModel
import com.sksingh.devthreads.viewmodel.UserViewModel

@Composable
fun Home(navHostController: NavHostController) {


    val homeViewModel: HomeViewModel = viewModel()
    val threadsAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)


    Column(modifier = Modifier.background(Color.Black)) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            painter = painterResource(id = R.drawable.devv),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit
        )


        LazyColumn(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {


            items(items = threadsAndUsers ?: emptyList()) { pairs ->
                PostItem(
                    thread = pairs.first,
                    users = pairs.second,
                   navHostController =  navHostController,
                  userId = FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }
    }
}