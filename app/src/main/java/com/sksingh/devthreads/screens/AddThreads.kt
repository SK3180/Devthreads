package com.sksingh.devthreads.screens


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Space
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

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.sksingh.devthreads.R
import com.sksingh.devthreads.navigations.Routes
import com.sksingh.devthreads.ui.theme.myfont2
import com.sksingh.devthreads.utils.SharedPref
import com.sksingh.devthreads.viewmodel.AddThreadViewModel


@Composable
fun AddThreads(navHostController: NavHostController){

    val ThreadViewModel : AddThreadViewModel = viewModel()
    val isPosted by ThreadViewModel.isPosted.observeAsState(false)
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

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
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ){

            isGranted:Boolean ->
        if (isGranted){
            Toast.makeText(null, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(null, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    var thread by remember {
        mutableStateOf("")
    }


    LaunchedEffect(isPosted) {
        if (isPosted == true) {
            if (thread.isBlank()) {
                Toast.makeText(context, "Enter something", Toast.LENGTH_SHORT).show()
                return@LaunchedEffect
            }

            // Proceed with posting if the thread is not empty
            thread = ""
            imageUri = null
            Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show()
            navHostController.navigate(Routes.Home.routes) {
                popUpTo(Routes.AddThreads.routes) {
                    inclusive = true
                }
            }
        }
    }

        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {

//            val (Cross,text,logo,userName,
//                editText,attachMedia,replyText,
//                button,imageBox) = createRefs()



            Column(modifier = Modifier.fillMaxSize()) {

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(imageVector = Icons.Rounded.Close,
                        contentDescription = "close",
                        colorFilter = ColorFilter.tint(color = Color.White),
                        modifier = Modifier
                            .size(55.dp)
                            .padding(start = 10.dp)
                            .clickable {
                                navHostController.navigate(Routes.Home.routes) {
                                    popUpTo(Routes.AddThreads.routes) {
                                        inclusive = true
                                    }
                                }
                            }
//                            .padding(top = 10.dp, start = 17.dp)
                    )
                    Text(text = "New Post",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = myfont2,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {

                    Image(painter =
                   rememberAsyncImagePainter(model = SharedPref.getImageUrl(context))
                        ,
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color.White), shape = CircleShape)
                            .clickable {

                            }

                    )
                    Text(text = SharedPref.getName(context),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = myfont2
                        )



                }
                TextField(hint = "What's Happening?",
                    thread,{thread = it},
                    modifier = Modifier)


                val gallery = painterResource(id = R.drawable.gallery)
                if (imageUri ==null ){
                    Image(painter = gallery,
                        contentDescription = "close",
                        modifier = Modifier
                            .size(55.dp)
                            .padding(start = 20.dp)
                            .clickable {

                                val isGranted = ContextCompat.checkSelfPermission(
                                    context, permissionToRequest
                                ) == PackageManager.PERMISSION_GRANTED

                                if (isGranted) {
                                    laucher.launch("image/*")
                                } else {
                                    permissionLauncher.launch(permissionToRequest)
                                }

                            }
                    )
                }
                else{
                    Box(modifier = Modifier
                        .padding(12.dp)
                        .height(250.dp)
                    )
                    {
                        Image(painter =
                        rememberAsyncImagePainter(model = imageUri),
                            contentDescription = "Gallery",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .clickable {

                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.remove) ,
                            contentDescription = "CLose",
                            colorFilter = ColorFilter.tint(color = Color.White),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(30.dp)
                                .clickable { imageUri = null }
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween, // Aligns children at start and end
                        verticalAlignment = Alignment.CenterVertically // Aligns children vertically at bottom
                    ) {
                        Text(
                            text = "Anyone can reply",
                            modifier = Modifier.padding(start = 10.dp),
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.SansSerif
                        )

                        TextButton(
                            modifier = Modifier.width(90.dp),
                            onClick = {
                                if (thread.isBlank()) {
                                    Toast.makeText(context, "Enter something", Toast.LENGTH_SHORT).show()
                                } else {
                                    if (imageUri == null) {
                                        ThreadViewModel.saveData(thread, FirebaseAuth.getInstance().currentUser!!.uid, "")
                                    } else {
                                        ThreadViewModel.saveImage(thread, FirebaseAuth.getInstance().currentUser!!.uid, imageUri!!)
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = "Post",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .background(
                                        Color(51, 153, 255),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(start = 10.dp, end = 10.dp),
                                textAlign = TextAlign.End,
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                    }
                }


            }



        }




}


@Composable
fun TextField(hint:String,value: String,onValueChange:(String)-> Unit,
              modifier: Modifier) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = hint, color = Color.Gray,
                modifier = Modifier.fillMaxWidth(), fontSize = 20.sp
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
//                        fontFamily = myfont2
            ),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 20,
        )


    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Testpreview()
{
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Aligns children at start and end
            verticalAlignment = Alignment.CenterVertically // Aligns children vertically at bottom
        ) {
            Text(
                text = "Anyone can reply",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            )

            TextButton(
                modifier = Modifier.width(90.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "Post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(
                            Color(51, 153, 255),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(start = 10.dp, end = 10.dp),
                    textAlign = TextAlign.End,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }



}
