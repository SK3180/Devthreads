package com.sksingh.devthreads.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sksingh.devthreads.R
import com.sksingh.devthreads.navigations.Routes
import com.sksingh.devthreads.viewmodel.AuthViewModel

@Composable
fun Register(navHostController: NavHostController){


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var bio by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)


    val permissionToRequest = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    }
    else
    {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val laucher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? ->
        imageUri = uri
    }
    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ){

        isGranted:Boolean ->
        if (isGranted){
            Toast.makeText(null, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(null, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(firebaseUser){
//        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        if (firebaseUser!=null){

            navHostController.navigate(Routes.BottomNav.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }





    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.uiapp),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Your existing UI content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 25.dp,
                    end = 25.dp,
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier
                .fillMaxSize()

//        .background(MaterialTheme.colorScheme.onBackground)
                , verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                val logoname = painterResource(id = R.drawable.devv)


                // Creating a Vertical Gradient Color

                Image(painter = logoname,
                    contentDescription = "LogoName",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(top = 10.dp)
                )

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 5.dp,
                        end = 5.dp,
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Register",
                        modifier = Modifier


                            .padding(bottom = 10.dp),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White


                    )

                    Image(
                        painter = if (imageUri == null) painterResource(id = R.drawable.profileicon)
                        else rememberAsyncImagePainter(model = imageUri),
                        contentDescription = "Person",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                border = BorderStroke(2.dp, color = Color.White),
                                shape = CircleShape
                            )
                            .background(Color.White, shape = CircleShape)
                            .clickable {

                                val isGranted = ContextCompat.checkSelfPermission(
                                    context, permissionToRequest
                                ) == PackageManager.PERMISSION_GRANTED

                                if (isGranted) {
                                    laucher.launch("image/*")
                                } else {
                                    permissionLauncher.launch(permissionToRequest)
                                }

                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
                    Card(
                        modifier = Modifier

                            .fillMaxWidth(),
                        shape = RoundedCornerShape(19.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Column(modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {



                        OutlinedTextField(value = name,
                            textStyle = TextStyle(color = Color.Black),
//                            colors = TextFieldDefaults.colors(Color.White),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = { name = it },
                            label = {
                                Text(text = "Name", color = Color.Black)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                        OutlinedTextField(value = email,
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = { email = it },
                            label = {
                                Text(text = "Email", color = Color.Black)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))

                        OutlinedTextField(value = username,
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = { username = it },
                            label = {
                                Text(text = "UserName", color = Color.Black)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))
                        OutlinedTextField(value = password,
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = { password = it },
                            label = {
                                Text(text = "Password", color = Color.Black)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))
                        OutlinedTextField(value = bio,
                            textStyle = TextStyle(color = Color.Black),
                            shape = RoundedCornerShape(10.dp),
                            onValueChange = { bio = it },
                            label = {
                                Text(text = "Bio", color = Color.Black)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))
                        ElevatedButton(colors = ButtonDefaults.buttonColors(
                            Color(
                                81,
                                196,
                                157,
                                255
                            )
                        ),
                            onClick = {

                                if (name.isEmpty() || bio.isEmpty() || password.isEmpty() || email.isEmpty() || username.isEmpty() || imageUri == null)
                                    Toast.makeText(
                                        context,
                                        "Please fill all details",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    authViewModel.register(
                                        email,
                                        password,
                                        name,
                                        username,
                                        bio,
                                        imageUri!!,
                                        context
                                    )
                                    Toast
                                        .makeText(
                                            context,
                                            "Successfully Registered",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                    navHostController.navigate(Routes.BottomNav.routes) {
                                        popUpTo(navHostController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }


                                }
                            },
                            modifier = Modifier
                                .padding(top = 30.dp, start = 30.dp, end = 30.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(15.dp)
                        )


                        {
                            Text(text = "Register",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 25.sp,
                                color = Color.White,
                            )

                        }
                        TextButton(
                            onClick = {

                                navHostController.navigate(Routes.Login.routes) {
                                    popUpTo(navHostController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(15.dp),
                        ) {
                            Text(
                                text = "Already register? Login here ",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0, 123, 255, 255)
                            )

                        }

                    }}
                }

            }
        }
    }





}

@Preview(showBackground = true)
@Composable
fun Test(){
//    Register()

    Image(painter = painterResource(id = R.drawable.profileicon),
        contentDescription = "Person",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable {

            },
        contentScale = ContentScale.Crop
    )
}