package com.sksingh.devthreads.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.sksingh.devthreads.R

import com.sksingh.devthreads.item_view.UserItem

import com.sksingh.devthreads.viewmodel.SearchViewModel

@Composable
fun Search(navHostController:NavHostController){


    val searchViewModel: SearchViewModel = viewModel()
    val UsersList by searchViewModel.UsersList.observeAsState(null)

    var search by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.background(Color.Black)) {



        Image( modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
            painter = painterResource(id = R.drawable.devv),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit)

            Column(modifier = Modifier.padding(20.dp)) {

                OutlinedTextField( value = search,
                    shape = RoundedCornerShape(20.dp),
                    textStyle = TextStyle(color = Color.White),
                    onValueChange = {search = it},
                    label = {
                    Text(text = "SearchUser",
                        color = Color.White)
                            },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true, modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = Color.White)
                    })

            }


        LazyColumn(modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()) {

            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (UsersList != null && UsersList!!.isNotEmpty()) {
                val filteredList = UsersList!!.filter { user ->
                    user.uid != userId
                }

                items(filteredList) { user ->
                    UserItem(
                        users = user,
                        navHostController
                    )
                }
            }
        }
    }

}