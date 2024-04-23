package com.example.cancerapp.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import com.example.cancerapp.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Signup(
    context: Context, coroutine: CoroutineScope, navController: NavController
) {

    var email by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }
    var Cpassword by remember {
        mutableStateOf("")
    }

    var passVisual by remember {
        mutableStateOf(true)
    }

    var blankInpt by remember {
        mutableStateOf(false)
    }




    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                            navController.navigate("home")
                        }
                    }, modifier = Modifier.padding(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
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
                    colors = CardDefaults.cardColors(Color.Black)
                ) {
                    Text(
                        text = "All fields must be field!",
                        color = Color.White,
                        modifier = Modifier.padding(5.dp, 8.dp)
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier

                    .padding(start = 2.dp)
                    .size(100.dp)
            )
            Text(
                text = "SIGNUP HERE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Username",color= Color.Black) },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences, false, KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )
        }


        item {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email",color= Color.Black) },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None, false, KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )
        }

        item {
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password",color= Color.Black) },
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
                visualTransformation = if (passVisual) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )
        }

        item {
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = Cpassword,
                onValueChange = { Cpassword = it },
                label = { Text(text = "Confirm Password",color= Color.Black) },
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
                visualTransformation = if (passVisual) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.08f)
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    coroutine.launch {

                        if (password.isNotEmpty() && email.isNotEmpty() && Cpassword.isNotEmpty()) {
                            navController.navigate("Login")

                        } else {
                            blankInpt = true

                            delay(500)
                            blankInpt = false
                        }

                        
                    }
                }, modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(42.dp),
                colors= ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFE91E63))
            ) {
                Text(text = "CREATE",color= Color.White)

            }
        }


        item {
            Spacer(modifier = Modifier.height(15.dp))
            Row (horizontalArrangement = Arrangement.Center , modifier = Modifier.fillMaxWidth(.8f)){
                Text(text = "Already have an Account?"

                )

                ClickableText(text = AnnotatedString("Login"),
                    style= TextStyle(color=Color(0xFFE91E63)),
                    onClick = {
                        coroutine.launch {
                            navController.navigate("Login")
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),


                )
            }

            Spacer(modifier = Modifier.height(15.dp))
        }



    }

}