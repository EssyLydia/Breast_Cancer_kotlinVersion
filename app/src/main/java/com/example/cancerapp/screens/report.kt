package com.example.cancerapp.screens

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cancerapp.R
import kotlinx.coroutines.CoroutineScope
import java.io.File
import java.io.FileOutputStream

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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {

                    val intent =
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                    //selectImage.launch(intent)

                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_insert_photo_24),
                    contentDescription = null
                )
                Text(text = "Select image")
            }
        }

        // Display classification results
        Text("Classification Results:")
        result?.forEach {
            Text(it)
        }

        // Patient information inputs
        TextField(
            value = patientName,
            onValueChange = { patientName = it },
            label = { Text("Patient Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = doctorName,
            onValueChange = { doctorName = it },
            label = { Text("Doctor Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )




        Button(onClick = {
            createAndSavePdf(null,patientName, age, gender,
                date, doctorName, context,result,
            )

        }) {
            Text("Generate and Download Report")
        }
    }
}

fun createAndSavePdf(
    imageUri: Uri?,

    patientName: String,
    age: String,
    gender: String,
    date: String,
    doctorName: String,
    context: Context,
    result: List<String>?
) {
    val pdfDocument = PdfDocument()

    // Start a new page
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size in points
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    // Draw the image if available
//    imageUri?.let { uri ->
//        val imageBitmap = loadImage(uri, context)
//        imageBitmap?.let { bitmap ->
//            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false)
//            val imageLeft = 50f
//            val imageTop = 50f
//            canvas.drawBitmap(scaledBitmap, imageLeft, imageTop, null)
//        }
//    }

    // Draw 'Report' text
    val reportText = "Report"
    val reportTextSize = 35f
    val reportTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = reportTextSize
    }
    val reportTextX = 300f // Adjust as needed
    val reportTextY = 50f // Adjust as needed
    canvas.drawText(reportText, reportTextX, reportTextY, reportTextPaint)

    // Draw the content text
    val contentTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 20f
    }
    val contentTextX = 50f // Adjust as needed
    val contentTextY = 250f // Adjust as needed
    result?.get(0)?.let { canvas.drawText(it, contentTextX, contentTextY, contentTextPaint) }

    // Draw patient information
    val patientInfoTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 20f
    }
    val patientInfoTextX = 50f // Adjust as needed
    val patientInfoTextY = 400f // Adjust as needed
    val lineSpacing = 20f
    canvas.drawText("Name: $patientName", patientInfoTextX, patientInfoTextY, patientInfoTextPaint)
    canvas.drawText(
        "Age: $age",
        patientInfoTextX,
        patientInfoTextY + lineSpacing,
        patientInfoTextPaint
    )
    canvas.drawText(
        "Gender: $gender",
        patientInfoTextX,
        patientInfoTextY + lineSpacing * 2,
        patientInfoTextPaint
    )
    canvas.drawText(
        "Date: $date",
        patientInfoTextX,
        patientInfoTextY + lineSpacing * 3,
        patientInfoTextPaint
    )
    canvas.drawText(
        "Doctor Name: $doctorName",
        patientInfoTextX,
        patientInfoTextY + lineSpacing * 4,
        patientInfoTextPaint
    )

    // Finish the page
    pdfDocument.finishPage(page)

    // Save the PDF to external storage
    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(dir, "my_pdf_file.pdf")

    try {
        val fos = FileOutputStream(file)
        pdfDocument.writeTo(fos)
        fos.close()
        pdfDocument.close()

        // Notify the user that the PDF has been saved
        Toast.makeText(context, "PDF saved successfully!", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error saving PDF", Toast.LENGTH_SHORT).show()
    }
}


//fun createAndSavePdf(content: String, context: Context) {
//    val pdfDocument = PdfDocument()
//    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size in points
//
//    val page = pdfDocument.startPage(pageInfo)
//    val canvas = page.canvas
//
//    // Draw your content on the canvas (e.g., text, images, etc.)
//    val paint = Paint()
//    paint.color = Color.BLACK
//    paint.textSize = 12f
//    canvas.drawText(content, 50f, 50f, paint)
//
//
//
//    pdfDocument.finishPage(page)
//
//    // Save the PDF to external storage
//    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//    val file = File(dir, "my_pdf_file.pdf")
//
//    try {
//        val fos = FileOutputStream(file)
//        pdfDocument.writeTo(fos)
//        fos.close()
//        pdfDocument.close()
//
//        // Notify the user that the PDF has been saved
//        Toast.makeText(context, "PDF saved successfully!", Toast.LENGTH_SHORT).show()
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Toast.makeText(context, "Error saving PDF", Toast.LENGTH_SHORT).show()
//    }
//}