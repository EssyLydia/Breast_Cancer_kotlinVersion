package com.example.cancerapp.screens

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cancerapp.R
import com.example.cancerapp.createAndSavePdf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Report(
    context: Context, coroutine: CoroutineScope, navController: NavController
) {
    var patientName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val result = navController.currentBackStackEntry?.arguments?.getString("results")?.split(",")
    var loader by remember {
        mutableStateOf(false)
    }

    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("Date") }


    val mDatePickerDialog = DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

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
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
        ) {
            Text("Classification Results", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(.95f)
            ) {
                Text(
                    text = "Benign: ", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = result?.get(0).toString(), modifier = Modifier.padding(5.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(.95f)
            ) {

                Text(
                    text = "Malignant: ", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = result?.get(1).toString(), modifier = Modifier.padding(5.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(.95f)
            ) {

                Text(
                    text = "Unknown: ", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = result?.get(2).toString(), modifier = Modifier.padding(5.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

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
            label = { Text("Contact") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )


        TextField(value = doctorName,
            onValueChange = { doctorName = it },
            label = { Text("Doctor Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = { mDatePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(Color(0xFFD1C9C9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    0.dp, Color.Unspecified, RoundedCornerShape(1.dp)
                ),

            ) {
            Text(text = mDate.value, color = Color.Black)
        }


        Button(onClick = {
            coroutine.launch {
                loader = true
                createAndSavePdf(
                    patientName, age, gender,
                    date, doctorName, context, result,
                )
                loader = false
            }

        }) {
            Text("Download Report")
        }
    }
    if (loader) Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x2C1565C0)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp))

    }

}
