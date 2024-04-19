package com.example.cancerapp.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cancerapp.ModelCode
import com.example.cancerapp.Predictions
import com.example.cancerapp.R

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun Diagnosis(
    coroutineScope: CoroutineScope,
    navController: NavController

) {
    val imageState = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val viewModel = ModelCode(context)
    var predictionResult by remember { mutableStateOf(Predictions()) }





    val selectImage =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri? = result.data?.data
                try {
                    val bitmap: Bitmap = BitmapFactory.decodeStream(
                        context.contentResolver.openInputStream(selectedImage!!)
                    )
                    imageState.value = bitmap

                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
                    val formattedDateTime = currentDateTime.format(formatter)

                    // Save the bitmap to a file
                    val imageName = "$formattedDateTime _tonsilSample.png"
                    val file = File(context.cacheDir, imageName)

                    val imageOutput = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutput)
                    imageOutput.close()

                    // Get the image path and pass it into the classifyImage() function
                    val imagePath = file.absolutePath
                    viewModel.classifyImage(imagePath)

                    predictionResult = viewModel.predictionResult.value!!

                    // Upload the image to your server
                    //uploadImageToServer(file, imageName, coroutineScope)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.2f)
                ) {
                    Text(
                        text = "Diagnosis And Classification",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))


                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .height(200.dp)
                ) {
                    imageState.value?.asImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CutCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .fillMaxHeight(.23f)
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(
                        onClick = {

                            val intent =
                                Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                            selectImage.launch(intent)

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .fillMaxHeight()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_insert_photo_24),
                            contentDescription = null
                        )
                        Text(text = "Upload image")
                    }
                }

                ElevatedCard(
                    modifier=Modifier
                        .fillMaxWidth(0.85f)
                        .padding(8.dp)

                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = " Results",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Benign: ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "${predictionResult.Benign}")
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Malignant: ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "${predictionResult.Malignant}")
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Undefined: ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "${predictionResult.Undefined}")
                            }



                        }
                    }
                }

                ElevatedButton(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                    coroutineScope.launch {
                    }
                }) {
                    Text(text = "Save", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                }

            }
        }
    }
}