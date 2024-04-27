package com.example.cancerapp

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
fun createAndSavePdf(
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

    // Draw the report title (bolder and serif font)
    val reportText = "Breast Cancer Diagnosis"
    val reportTextSize = 40f
    val reportTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = reportTextSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD) // Set font family to serif and make it bold
    }
    val reportTextX = pageInfo.pageWidth / 2f
    val reportTextY = 35f // Adjust as needed
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



    // Draw the light blue table (3 rows, 2 columns)
    val tablePaint = Paint().apply {
        color = Color.parseColor("#ADD8E6") // Light blue color
    }
    val cellWidth = (pageInfo.pageWidth - 50f) / 2f
    val cellHeight = 50f
    val tableStartY = lineY + 50f // Position below the "Results" text

    // Draw table cells without loops
    val cellX1 = 50f
    val cellX2 = cellWidth
    val cellY1 = tableStartY
    val cellY2 = tableStartY + cellHeight
    val cellTextPaint = Paint().apply {
        color = Color.BLACK
        textSize = 20f
        textAlign = Paint.Align.LEFT
    }


    // Draw cell 1 (Row 1, Col 1)
    canvas.drawRect(cellX1, cellY1, cellX2, cellY2, tablePaint)
    val cellText1 = "Benign"
    canvas.drawText(cellText1, (cellX1 + cellX2) / 2f, (cellY1 + cellY2) / 2f, cellTextPaint)

    // Draw cell 2 (Row 1, Col 2)
    canvas.drawRect(cellX2, cellY1, cellX2 + cellWidth, cellY2, tablePaint)
    val cellText2 = result?.get(0)
    if (cellText2 != null) {
        canvas.drawText(cellText2, (cellX2 + cellX2 + cellWidth) / 2f, (cellY1 + cellY2) / 2f, cellTextPaint)
    }

    // Draw cell 3 (Row 2, Col 1)
    canvas.drawRect(cellX1, cellY2, cellX2, cellY2 + cellHeight, tablePaint)
    val cellText3 = "Malignant"
    canvas.drawText(cellText3, (cellX1 + cellX2) / 2f, (cellY2 + cellY2 + cellHeight) / 2f, cellTextPaint)

    // Draw cell 4 (Row 2, Col 2)
    canvas.drawRect(cellX2, cellY2, cellX2 + cellWidth, cellY2 + cellHeight, tablePaint)
    val cellText4 = result?.get(1)
    if (cellText4 != null) {
        canvas.drawText(cellText4, (cellX2 + cellX2 + cellWidth) / 2f, (cellY2 + cellY2 + cellHeight) / 2f, cellTextPaint)
    }

    // Draw cell 5 (Row 3, Col 1)
    canvas.drawRect(cellX1, cellY2 + cellHeight, cellX2, cellY2 + 2 * cellHeight, tablePaint)
    val cellText5 = "UnDefined"
    canvas.drawText(cellText5, (cellX1 + cellX2) / 2f, (cellY2 + cellY2 + 2 * cellHeight) / 2f, cellTextPaint)

    // Draw cell 6 (Row 3, Col 2)
    canvas.drawRect(cellX2, cellY2 + cellHeight, cellX2 + cellWidth, cellY2 + 2 * cellHeight, tablePaint)
    val cellText6 = result?.get(2)
    if (cellText6 != null) {
        canvas.drawText(cellText6, (cellX2 + cellX2 + cellWidth) / 2f, (cellY2 + cellY2 + 2 * cellHeight) / 2f, cellTextPaint)
    }



    // Finish the page
    pdfDocument.finishPage(page)

    // Save the PDF to external storage
    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(dir, "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"))}_Patient Report.pdf")

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
