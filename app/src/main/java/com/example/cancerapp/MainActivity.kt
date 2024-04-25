package com.example.cancerapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cancerapp.screens.Diagnosis
import com.example.cancerapp.screens.Home
import com.example.cancerapp.screens.Login
import com.example.cancerapp.screens.Recover
import com.example.cancerapp.screens.Report
import com.example.cancerapp.screens.Screen1
import com.example.cancerapp.screens.Screen2
import com.example.cancerapp.screens.Screen3
import com.example.cancerapp.screens.Signup

import com.example.cancerapp.ui.theme.CancerappTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CancerappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFDC2D7)
                ) {
                    val navController = rememberNavController()
                    val coroutineScope = rememberCoroutineScope()
                    val context = LocalContext.current
                    NavHost(navController = navController, startDestination = "Screen1") {
                        composable("home") {
                            Home(coroutineScope, context, navController)
                        }
                        composable("diagnosis") {
                            Diagnosis(coroutineScope, navController)
                        }
                        composable("Screen1") {
                            Screen1(coroutineScope, context, navController)

                        }
                        composable("Screen2") {
                            Screen2(coroutineScope, context, navController)

                        }
                        composable("Screen3") {
                            Screen3(coroutineScope, context, navController)

                        }
                        composable("Login") {
                            Login( context, coroutineScope,navController)

                        }
                        composable("Signup") {
                            Signup( context, coroutineScope,navController)

                        }
                        composable("Recover") {
                            Recover( context, coroutineScope,navController)

                        }

                        composable("Report/{results}") {
                            Report( context, coroutineScope,navController)

                        }


                    }
                }
            }
        }
    }
}

