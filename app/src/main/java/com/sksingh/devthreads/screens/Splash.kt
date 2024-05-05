package com.sksingh.devthreads.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.sksingh.devthreads.R
import com.sksingh.devthreads.navigations.Routes
import kotlinx.coroutines.delay


@Composable
fun Splash(navController: NavHostController){

    SplashLogo()
    LaunchedEffect(true){
        delay(2000)

        if(FirebaseAuth.getInstance().currentUser!=null)
        navController.navigate(Routes.BottomNav.routes){
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
        else
            navController.navigate(Routes.Login.routes)
        {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SplashLogo(){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
//        .background(MaterialTheme.colorScheme.onBackground)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logo:Painter = painterResource(id = R.drawable.devthread)
        Image(painter = logo, contentDescription = "Logo", modifier = Modifier.size(200.dp))

    }
}