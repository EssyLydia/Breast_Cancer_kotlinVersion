package com.example.cancerapp.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cancerapp.R

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Recover(context: Context, coroutine: CoroutineScope, navController: NavController) {

    var username by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    var confirm by remember {
        mutableStateOf("")
    }

    var blankInpt by remember {
        mutableStateOf(false)
    }
    var passVisual by remember {
        mutableStateOf(true)
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)

            ) {
                IconButton(
                    onClick = {
                        coroutine.launch {
                            navController.navigate("Login")
                        }
                    }, modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(.8f)
                    )
                }

            }
        }
        if (blankInpt) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(Color(0xFFA83232))
                ) {
                    Text(
                        text = "Please fill in all Input fields!",
                        color = Color.White,
                        modifier = Modifier.padding(5.dp, 8.dp)
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 2.dp)
            )
            Text(text = "Reset your password here", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Enter your new password below")

        }

        item {

            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = username,
                colors=TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent),
                onValueChange = { username = it },
                label = { Text(text = "Username") },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None, false, KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = pass,
                colors=TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent),
                onValueChange = { pass = it },
                label = { Text(text = "New Password") },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None, false, KeyboardType.Password
                ),
                visualTransformation = if (passVisual) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { passVisual = !passVisual }) {
                        Icon(
                            painter = if (passVisual) painterResource(id = R.drawable.baseline_visibility_off_24) else painterResource(
                                id = R.drawable.baseline_visibility_24
                            ),
                            contentDescription = null
                        )
                    }

                },

                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )


            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = confirm,
                colors=TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent),
                onValueChange = { confirm = it },
                label = { Text(text = "Confirm Password") },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None, false, KeyboardType.Password
                ),
                visualTransformation = if (passVisual) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { passVisual = !passVisual }) {
                        Icon(
                            painter = if (passVisual) painterResource(id = R.drawable.baseline_visibility_off_24) else painterResource(
                                id = R.drawable.baseline_visibility_24
                            ),
                            contentDescription = null
                        )
                    }

                },

                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )
        }


        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    coroutine.launch {
                        if (username.isNotEmpty())
                            navController.navigate("Login")
                        else {
                            blankInpt = true
                            delay(2500)

                            blankInpt = false
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(42.dp)
            ) {
                Text(text = "Reset")
            }
        }

    }
}

