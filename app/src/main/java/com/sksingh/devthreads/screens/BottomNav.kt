package com.sksingh.devthreads.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sksingh.devthreads.models.BottomNavItem
import com.sksingh.devthreads.navigations.Routes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BottomNav(navController: NavHostController){

    val navController1 = rememberNavController()

    Scaffold(bottomBar = { Mybottombar(navController1) }) { innerPadding ->
        NavHost(navController = navController1, startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)){
            composable(route = Routes.Home.routes){
                Home(navController)
            }
            composable(route = Routes.Notification.routes){
                Notification()
            }
            composable(route = Routes.Search.routes){
                Search(navController)
            }
            composable(route = Routes.AddThreads.routes){
                AddThreads(navController1)
            }
            composable(route = Routes.Profile.routes){
                Profile(navController)
            }
        }


    }


}

@Composable
fun Mybottombar(navController1: NavHostController) {

    val backStackEntry =  navController1.currentBackStackEntryAsState()
//    val home = painterResource(id = R.drawable.home)
//    val search = painterResource(id = R.drawable.search)
//    val add = painterResource(id = R.drawable.add)
//    val notification = painterResource(id = R.drawable.notification)
//    val profile = painterResource(id = R.drawable.home)
    val list = listOf(
        BottomNavItem(
            "home",
            Routes.Home.routes,
            Icons.Rounded.Home
        ), BottomNavItem(
            "Search",
        Routes.Search.routes,
        Icons.Rounded.Search
    ),
    BottomNavItem(
        "Add Threads",
        Routes.AddThreads.routes,
        Icons.Rounded.Add
    ),
    BottomNavItem(
        "Notification",
        Routes.Notification.routes,
        Icons.Rounded.Notifications
    ),
    BottomNavItem(
        "Profile",
        Routes.Profile.routes,
        Icons.Rounded.Person
    )

    )

    BottomAppBar(containerColor = Color.Black) {
        list.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route
            var color = Color.White
            var isScaled by remember { mutableStateOf(false) } // Move inside the loop

            if (selected) {
                color = Color(81, 196, 157, 255)
                isScaled = true
                LaunchedEffect(Unit) {
                    delay(200) // Adjust the duration as needed
                    isScaled = false
                }
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController1.navigate(it.route) {
                        popUpTo(navController1.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        tint = color,
                        modifier = Modifier.scale(if (isScaled) 1.3f else 1f),
                        contentDescription = it.title
                    )
                }
            )
        }
    }
    }




