package com.sksingh.devthreads.navigations

sealed class Routes(val routes:String) {


    data object Home :  Routes("home")
    data object AddThreads :  Routes("add")
    data object BottomNav :  Routes("bottom")
    data object Notification :  Routes("notification")
    data object Profile :  Routes("profile")
    data object Search  :  Routes("search")
    data object Splash :  Routes("splash")
    data object Login :  Routes("login")
    data object Register :  Routes("register")
//    data object OtherUser :  Routes("other_users/{data}")
    data object OtherUser :  Routes("other_users/{data}")




}