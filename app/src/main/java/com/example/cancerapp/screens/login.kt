package com.example.cancerapp.screens


import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cancerapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun Login(
    context: Context,
    coroutine: CoroutineScope,
    navController: NavController,

    ) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 2.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
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
                        text = " Invalid login details",
                        color = Color.White,
                        modifier = Modifier.padding(5.dp, 8.dp)
                    )
                }

            }
        }

        item {
            Spacer(modifier = Modifier.height(13.dp))
            Text(text = "WELCOME BACK", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Log in to your account", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Username", color = Color.Black) },
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
                label = { Text(text = "Password", color = Color.Black) },
                trailingIcon = {
                    IconButton(onClick = {
                        passVisual = !passVisual

                    }) {
                        Icon(
                            painter = if (passVisual) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(
                                id = R.drawable.baseline_visibility_off_24
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
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth(.8f)) {
                TextButton(onClick = {
                    coroutine.launch {
                        navController.navigate("Recover")
                    }
                }) {
                    Text(
                        text = "Forgot Password?",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            color =MaterialTheme.colorScheme.primary
                        )
                    )

                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    coroutine.launch {
                        navController.navigate("Diagnosis")
//                        if (password.isNotEmpty() && email.isNotEmpty()) {
//                            val rs = user.getUser(email, password)
//                            if (rs != null && rs.email == email && rs.pass == password)

//                            else{
//                                blankInpt = true
//                                delay(2500)
//
//                                blankInpt = false
//                            }
//                        }
//                        else {
//                            blankInpt = true
//                            delay(2500)
//
//                            blankInpt = false
//                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(42.dp)
            ) {
                Text(text = "Login", color = Color.White)
            }
        }

        item {
            Spacer(modifier = Modifier.height(15.dp))
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(.8f)){
                Text(text = "Don't have an account?")
                ClickableText(
                    text = AnnotatedString("Signup"),
                    style= TextStyle(color=Color(0xFFE91E63)),
                    onClick = {
                        coroutine.launch {
                            navController.navigate("Signup")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),


                    )
            }

        }


//

    }
}