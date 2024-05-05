package com.sksingh.devthreads.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.sksingh.devthreads.R
import com.sksingh.devthreads.navigations.Routes
import com.sksingh.devthreads.ui.theme.myfont2
import com.sksingh.devthreads.viewmodel.AuthViewModel

@Composable
fun Login(navController: NavHostController) {

    val authViewModel: AuthViewModel = viewModel()

    val firebaseUser by authViewModel.firebaseUser.observeAsState()

    val context = LocalContext.current

    val error by authViewModel.error.observeAsState()


    LaunchedEffect(firebaseUser) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {

            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }
    error?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {





        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.uiapp),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds // Adjust content scale as needed
            )
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val logoname = painterResource(id = R.drawable.devv)

                // Creating a Vertical Gradient Color
                Image(
                    painter = logoname,
                    contentDescription = "LogoName",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(top = 10.dp)
                )
            }

            // Your existing UI content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                val loginimg = painterResource(id = R.drawable.login)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = loginimg,
                        contentDescription = "login image",
                        modifier = Modifier.size(150.dp),
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Crop
                    )
                }

                // Enclose the Card within this Column
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    // Wrap the Card's height to its content
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp), shape = RoundedCornerShape(19.dp),
                               colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Login",
                                modifier = Modifier.padding(bottom = 10.dp),
                                color = Color.Black,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Serif,

                            )

                            OutlinedTextField(
                                textStyle = TextStyle(color = Color.Black),
                                value = email,
                                shape = RoundedCornerShape(10.dp),
                                onValueChange = { email = it },
                                label = {
                                    Text(
                                        text = "Email",
                                        color = Color.Gray,
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(bottom = 10.dp))

                            OutlinedTextField(
                                textStyle = TextStyle(color = Color.Black),
                                value = password,
                                shape = RoundedCornerShape(10.dp),
                                onValueChange = { password = it },
                                label = {
                                    Text(
                                        text = "Password",
                                        color = Color.Gray
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            ElevatedButton(
                                colors = ButtonDefaults.buttonColors(Color(81, 196, 157, 255)),
                                onClick = {
                                    if (email.isEmpty() || password.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Please enter all details",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        authViewModel.login(email, password, context)
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 30.dp, start = 30.dp, end = 30.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(15.dp),
                            ) {
                                Text(
                                    text = "Submit",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }

                            TextButton(
                                onClick = {
                                    navController.navigate(Routes.Register.routes) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(15.dp),
                            ) {
                                Text(
                                    text = "New User? Create Account ",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0, 123, 255, 255)
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}
//        val loginimg = painterResource(id = R.drawable.login)


//        Column(modifier = Modifier.fillMaxWidth()
//            ,verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            Image(painter = loginimg,
//                contentDescription = "login image",
//                modifier = Modifier.size(150.dp)
//                ,
//                alignment = Alignment.TopCenter,contentScale = ContentScale.Crop )
//        }


