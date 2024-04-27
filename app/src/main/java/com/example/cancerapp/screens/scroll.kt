package com.example.cancerapp.screens



import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cancerapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Screen1(coroutine: CoroutineScope, context: Context, navController: NavController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.doctor_8482048),
                contentDescription = null,
                modifier = Modifier.size(400.dp),

                )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Did you know that breast cancer does not always come in form of a lump",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE91E63),
                modifier = Modifier.fillMaxWidth(.85f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = {

                    coroutine.launch {
                        navController.navigate("Screen2")
                    }
                }, modifier = Modifier.fillMaxWidth(.5f),
                colors= ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFE91E63))


            ) {
                Text(text = "Next", color = Color.White)
            }
        }
    }

}
@Composable
fun Screen2(coroutine: CoroutineScope, context: Context, navController: NavController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.vect1),
                contentDescription = null,
                modifier = Modifier.size(400.dp),

                )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Having a relative that has had breast cancer increases your chances of getting it",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE91E63),
                modifier = Modifier.fillMaxWidth(.85f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = {

                    coroutine.launch {
                        navController.navigate("Screen3")
                    }
                }, modifier = Modifier.fillMaxWidth(.5f),
                colors= ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFE91E63))


            ) {
                Text(text = "Next", color = Color.White)
            }
        }
    }

}
@Composable
fun Screen3(coroutine: CoroutineScope, context: Context, navController: NavController){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.asset_1),
                contentDescription = null,
                modifier = Modifier.size(400.dp),

                )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Early diagnosis of breast cancer can increase survival rates worldwide",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE91E63),
                modifier = Modifier.fillMaxWidth(.85f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            ElevatedButton(
                onClick = {

                    coroutine.launch {
                        navController.navigate("home")
                    }
                }, modifier = Modifier.fillMaxWidth(.5f),
                colors= ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFE91E63))


            ) {
                Text(text = "Next", color = Color.White)
            }
        }
    }

}







