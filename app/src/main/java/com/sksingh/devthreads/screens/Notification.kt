package com.sksingh.devthreads.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Notification(){
Column(
    Modifier
        .background(Color.Black)
        .fillMaxSize()) {



    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = "Notifications",
            fontSize = 25.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
            ,
            textAlign = TextAlign.Center, color = Color.White
        )
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center) {
            Text(text = "No notifications ",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }




    }
}


}