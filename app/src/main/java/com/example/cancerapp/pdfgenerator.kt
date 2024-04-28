package com.example.cancerapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun createAndSavePdf(
    imagePath: String,
    patientName: String,
    age: String,
    address: String,
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

    // Draw the report title (bolder and serif font)
    val reportText = "Breast Cancer Diagnosis"
    val reportTextSize = 40f
    val reportTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = reportTextSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD) // Set font family to serif and make it bold
    }
    val reportTextX =  30f + pageInfo.pageWidth / 2f
    val reportTextY = 50f // Adjust as needed
    canvas.drawText(reportText, reportTextX, reportTextY, reportTextPaint)

    // Draw an additional heading
    val additionalHeading = "Report"
    val additionalHeadingSize = 40f
    val additionalHeadingPaint = Paint().apply {
        color = Color.BLACK
        textSize = additionalHeadingSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }
    val additionalHeadingY = reportTextY + 80f // Position below the first heading
    canvas.drawText(additionalHeading, reportTextX, additionalHeadingY, additionalHeadingPaint)

        // Draw the "Results" text (font size 25 and left-aligned)
    val resultsText = "Results"
    val resultsTextSize = 28f
    val resultsTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = resultsTextSize
        textAlign = Paint.Align.LEFT
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }
    val resultsTextX = 50f // Adjust as needed
    val resultsTextY = reportTextY + 120f // Position below the report title
    canvas.drawText(resultsText, resultsTextX, resultsTextY, resultsTextPaint)

    // Draw a horizontal solid line below the "Results" text
    val lineStartX = 50f
    val lineEndX = pageInfo.pageWidth - 50f
    val lineY = resultsTextY + 30f // Position below the additional heading
    val linePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 2f
    }
    canvas.drawLine(lineStartX, lineY, lineEndX, lineY, linePaint)

    ///////////-------------------------------------------------
    // Draw a blue rectangle
    val rectanglePaint = Paint().apply {
        color = Color.GRAY
    }
    val rectLeft = 50f
    val rectTop = lineY + 50f
    val rectRight = pageInfo.pageWidth / 2f + 50
    val rectBottom = 500f
    canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, rectanglePaint)

    // Draw text inside the rectangle (left and right)
    val rightText1 = result?.get(0)
    val rightText2 = result?.get(1)
    val rightText3 = result?.get(2)

    val rightText4 = mutableListOf("Bengin", "0.0%")

    if (rightText1?.toFloat()!! > rightText2?.toFloat()!! && rightText1?.toFloat()!! > rightText3?.toFloat()!!){
        rightText4[0] = "Bengin"
        rightText4[1] = rightText1
    }else if (rightText2?.toFloat()!! > rightText1?.toFloat()!! && rightText2?.toFloat()!! > rightText3?.toFloat()!!){
        rightText4[0] = "Malignant"
        rightText4[1] = rightText2
    }else{
        rightText4[0] = "UnDefined"
        if (rightText3 != null) {
            rightText4[1] = rightText3
        }
    }


    val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 24f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }


    canvas.drawText("Bengin:", rectLeft + 20f, rectTop + 50f, textPaint)
    if (rightText1 != null) {
        canvas.drawText("$rightText1%", rectRight - 125f, rectTop + 50f, textPaint)
    }

    canvas.drawText("Malignant:", rectLeft + 20f, rectTop + 100f, textPaint)
    if (rightText2 != null) {
        canvas.drawText("$rightText2%", rectRight - 125f, rectTop + 100f, textPaint)
    }

    canvas.drawText("Unknown:", rectLeft + 20f, rectTop + 150f, textPaint)
    if (rightText3 != null) {
        canvas.drawText("$rightText3%", rectRight - 125f, rectTop + 150f, textPaint)
    }

    val textPaint3 = Paint().apply {
        color = Color.RED
        textSize = 18f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }
    canvas.drawText("Final Results:", rectLeft + 20f, rectTop + 210f, textPaint3)
    if (rightText4 != null) {
        canvas.drawText(rightText4[0], rectRight - 135f, rectTop + 210f, textPaint3)
    }



    ///////////-------------------------------------------------

    // Position the image to the right of the blue rectangle
    val imageX = rectRight + 8f // Adjust as needed
    val imageY = rectTop + 40f // Adjust as needed

    val originalBitmap = BitmapFactory.decodeFile(imagePath)
    val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, false)
    canvas.drawBitmap(scaledBitmap, imageX, imageY, Paint())

    val textPaint2 = Paint().apply {
        color = Color.BLACK
        textSize = 24f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }
    canvas.drawText("--Sample--", rectRight+25, rectTop + 15, textPaint2)

    val textPaint1 = Paint().apply {
        color = Color.GREEN
        textSize = 25f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
    }

    //----------Doctor Section
    val docX = rectLeft
    val docY = rectBottom + 40f
    canvas.drawText("Doctor: ", docX, docY, textPaint1)
    if (rightText4 != null) {
        canvas.drawText(doctorName, rectRight - 140f, rectBottom + 40f, textPaint1)
    }
    //---------End of Doctor section

    //------------------Patient----------------------
    // Draw a horizontal solid line below the "Results" text
    val lineStart2X = 50f
    val lineEnd2X = pageInfo.pageWidth - 50f
    val line2Y = docY + 20f // Position below the additional heading
    canvas.drawLine(lineStart2X, line2Y, lineEnd2X, line2Y, linePaint)
    canvas.drawText("Patient Details: ", docX, line2Y+30, textPaint2)
    canvas.drawLine(lineStart2X, line2Y+40, lineEnd2X, line2Y+40, linePaint)
    val text2Y = line2Y + 70
    canvas.drawText("Names: ", docX, text2Y, textPaint2)
    canvas.drawText(patientName, rectRight-160, text2Y, textPaint2)

    canvas.drawText("Age: ", docX, text2Y+30, textPaint2)
    canvas.drawText(age, rectRight-160, text2Y+30, textPaint2)

    canvas.drawText("Address: ", docX, text2Y+60, textPaint2)
    canvas.drawText(address, rectRight-160, text2Y+60, textPaint2)

    val textPaint5 = Paint().apply {
        color = Color.BLACK
        textSize = 20f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
    }
    canvas.drawLine(lineStart2X, text2Y+70, lineEnd2X, text2Y+70, linePaint)
    canvas.drawText("Date of Diagnosis: ", docX, text2Y+95, textPaint5)
    canvas.drawText(date, rectRight-35, text2Y+95, textPaint5)
    //-----------------------------------




    // Finish the page
    pdfDocument.finishPage(page)

    // Save the PDF to external storage
    val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val file = File(dir, "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))}_Patient Report.pdf")
    val xfile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))}_Patient Report.pdf")

    try {
        val fos = FileOutputStream(xfile)
        pdfDocument.writeTo(fos)
        fos.close()
        pdfDocument.close()

        // Notify the user that the PDF has been saved
        Toast.makeText(context, "PDF saved successfully!", Toast.LENGTH_SHORT).show()

    }catch (e: FileNotFoundException){
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


//    try {
//        // Open PDF using FileProvider
//        val intent = Intent(Intent.ACTION_VIEW)
//        val uri = FileProvider.getUriForFile(context, "com.example.cancerapp.reports", file)
//        intent.setDataAndType(uri, "application/pdf")
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        context.startActivity(intent)
//    }catch (e: Exception){
//        e.printStackTrace()
//        Toast.makeText(context, "Failed to open PDF", Toast.LENGTH_SHORT).show()
//    }

}


