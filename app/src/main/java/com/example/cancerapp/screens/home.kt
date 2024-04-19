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
fun Home(coroutine: CoroutineScope, context: Context, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter= painterResource(id =R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(400.dp),

            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "BREAST CANCER DIAGNOSIS AND CLASSIFICATION",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE91E63),
                modifier = Modifier.fillMaxWidth(.85f)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "An AI based solution for diagnosis and classification of breast cancer subtypes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(.85f)
            )

            Spacer(modifier = Modifier.height(10.dp))
            ElevatedButton(
                onClick = {

                    coroutine.launch {
                        navController.navigate("diagnosis")
                    }
                }, modifier = Modifier.fillMaxWidth(.85f),
                   colors= ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFE91E63))


            ) {
                Text(text = "Get Started", color = Color.White)
            }
        }
    }

}