package com.example.cancerapp.screens

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cancerapp.R
import com.example.cancerapp.createAndSavePdf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Report(
    context: Context, navController: NavController
) {
    val coroutine = rememberCoroutineScope()
    var patientName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val result = navController.currentBackStackEntry?.arguments?.getString("results")?.split(",")
    Log.d(null, "--------Gotten results--------")
    val sickImg =
        navController.currentBackStackEntry?.arguments?.getString("imagePath")?.replace("__", "/")
    Log.d(null, "--------Gotten ImagePath ${sickImg}--------")
    var loader by remember {
        mutableStateOf(false)
    }

    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    var mDate by remember { mutableStateOf("--Select Date--") }


    val mDatePickerDialog = DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    if (loader) AlertDialog(onDismissRequest = {}, confirmButton = {}, dismissButton = {

    }, title = {
        Box(
            modifier = Modifier
                .fillMaxHeight(.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Pdf Generation", fontWeight = FontWeight.Bold, fontSize = 22.sp, textAlign = TextAlign.Center)
        }
    }, text = {
        Column(
            modifier = Modifier
                .fillMaxHeight(.15f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please be Patient as the document is being processed",
                softWrap = true,
                modifier = Modifier.padding(2.dp),
                fontSize = 20.sp, textAlign = TextAlign.Center
            )
            CircularProgressIndicator(modifier = Modifier.padding(3.dp), strokeWidth = 3.dp)
        }
    })

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .fillMaxHeight(.23f)
                        .padding(vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.butterfly_8127622),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),

                        )
                    Text(
                        "Generate Report",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // Display classification results
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Classification Results",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Divider(modifier = Modifier.padding(vertical = 2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(.95f)
                    ) {
                        Text(
                            text = "Benign: ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = result?.get(0).toString() + "%",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(.95f)
                    ) {

                        Text(
                            text = "Malignant: ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = result?.get(1).toString() + "%",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(.95f)
                    ) {

                        Text(
                            text = "Unknown: ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = result?.get(2).toString() + "%",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Patient Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextField(value = patientName,
                            onValueChange = { patientName = it },
                            label = { Text("Patient Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        TextField(value = age,
                            onValueChange = { age = it },
                            label = { Text("Age") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
//
                        )

                        TextField(value = gender,
                            onValueChange = { gender = it },
                            label = { Text("Address") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }


                TextField(value = doctorName,
                    onValueChange = { doctorName = it },
                    label = { Text("Doctor Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Button(
                    onClick = { mDatePickerDialog.show() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD1C9C9),
                        disabledContainerColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0xFFD1C9C9), RoundedCornerShape(1.dp))
                ) {
                    Text(text = mDate, color = Color.Black)
                }


                Button(modifier = Modifier.fillMaxWidth(.95f), onClick = {
                    coroutine.launch {
                        loader = true
                        Log.d(null, "--------Processing PDF--------")
                        if (sickImg != null) {
                            createAndSavePdf(
                                sickImg,
                                patientName, age, gender,
                                mDate, doctorName, context, result,
                            )
                        }

                        Log.d(null, "--------FINISHED PROCESSING PDF--------")
                        delay(2500)
                        loader = false
                        navController.navigate("diagnosis")
                    }

                }) {
                    Text("Download Report")
                }

                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }

}
