package com.project.kasirku.presentation.Kasir

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.project.kasirku.R
import com.project.kasirku.model.Orders
import java.io.File
import java.io.FileOutputStream

fun generatePdf(order: Orders, context: Context) {
    // Ukuran kertas struk: Lebar 80mm (226 piksel) dan panjang dinamis
    val paperWidth = 226
    var paperHeight = 400 // Mulai dengan panjang minimum, bisa ditambah nanti

    // Hitung panjang berdasarkan item yang dibeli
    val estimatedHeightPerItem = 40
    paperHeight += order.items.size * estimatedHeightPerItem

    // Membuat dokumen PDF dengan ukuran struk
    val pageInfo = PdfDocument.PageInfo.Builder(paperWidth, paperHeight, 1).create() // Ukuran kertas struk
    val pdfDocument = PdfDocument()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()
    val boldPaint = Paint().apply {
        textSize = 12f // Ukuran font lebih kecil sesuai struk
        typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
    }
    val regularPaint = Paint().apply {
        textSize = 10f // Ukuran font lebih kecil sesuai struk
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }

    val margin = 10f // Padding kiri dan kanan

    // NamaAplikasi
    var yPosition = 60f
    val namaAplikasi = "Kasir.Ku"
    canvas.drawText(namaAplikasi, (paperWidth - boldPaint.measureText(namaAplikasi)) / 2, yPosition, boldPaint) //

    // Nama Toko di Tengah
    yPosition = 80f
    val namaTokoText = "Ursweethingsq"
    canvas.drawText(namaTokoText, (paperWidth - boldPaint.measureText(namaTokoText)) / 2, yPosition, boldPaint) // Center
    yPosition += 20f
    val alamatText = "Bekasi"
    canvas.drawText(alamatText, (paperWidth - regularPaint.measureText(alamatText)) / 2, yPosition, regularPaint) // Center

    // Garis pemisah
    yPosition += 20f
    paint.strokeWidth = 1f
    canvas.drawLine(margin, yPosition, paperWidth - margin, yPosition, paint)

    // Tanggal dan Kasir
    yPosition += 20f
    val tanggalText = "Kamis, ${order.tanggal}"
    canvas.drawText(tanggalText, margin, yPosition, regularPaint)
    yPosition += 15f
    val kasirText = "Kasir: ${order.kasir}"
    canvas.drawText(kasirText, margin, yPosition, regularPaint)

    // Garis pemisah lagi
    yPosition += 20f
    canvas.drawLine(margin, yPosition, paperWidth - margin, yPosition, paint)

    // List produk yang dibeli
    // List produk yang dibeli
    regularPaint.textSize = 8f // Ukuran font lebih kecil untuk list produk
    yPosition += 20f
    var itemCount = 1
    order.items.forEach { item ->
        // Nama Produk
        boldPaint.textSize = 8f // Ukuran font lebih kecil untuk nama produk
        val namaProduk = "${itemCount}. ${item.namaProduk}"
        canvas.drawText(namaProduk, margin, yPosition, boldPaint)
        yPosition += 12f // Sesuaikan jarak agar lebih kecil
        // Quantity dan Harga per item
        val quantityHargaText = "${item.quantity} x Rp. ${item.hargaJual}"
        canvas.drawText(quantityHargaText, margin, yPosition, regularPaint)
        // Harga total item di kanan
        val hargaTotalText = "Rp. ${item.quantity * item.hargaJual}"
        canvas.drawText(hargaTotalText, paperWidth - margin - regularPaint.measureText(hargaTotalText), yPosition, regularPaint)
        yPosition += 15f // Sesuaikan jarak agar lebih kecil
        itemCount++
    }


    // Garis pemisah
    canvas.drawLine(margin, yPosition, paperWidth - margin, yPosition, paint)
    yPosition += 20f

    // Total Harga, Bayar, Kembali
    regularPaint.textSize = 8f // Ukuran font lebih kecil untuk total, bayar, kembali
    val totalText = "Total :"
    val totalHargaText = "Rp. ${order.totalHarga}"
    canvas.drawText(totalText, margin, yPosition, regularPaint)
    canvas.drawText(totalHargaText, paperWidth - margin - regularPaint.measureText(totalHargaText), yPosition, regularPaint) // Total di kanan
    yPosition += 12f // Kurangi jarak agar sesuai dengan ukuran teks yang lebih kecil
    val bayarText = "Bayar :"
    val bayarAmountText = "Rp. ${order.bayar}"
    canvas.drawText(bayarText, margin, yPosition, regularPaint)
    canvas.drawText(bayarAmountText, paperWidth - margin - regularPaint.measureText(bayarAmountText), yPosition, regularPaint) // Bayar di kanan
    yPosition += 12f
    val kembaliText = "Kembali :"
    val kembaliAmountText = "Rp. ${order.kembali}"
    canvas.drawText(kembaliText, margin, yPosition, regularPaint)
    canvas.drawText(kembaliAmountText, paperWidth - margin - regularPaint.measureText(kembaliAmountText), yPosition, regularPaint) // Kembali di kanan


    // Garis pemisah lagi
    yPosition += 20f
    canvas.drawLine(margin, yPosition, paperWidth - margin, yPosition, paint)

    // Pesan Terima Kasih di Tengah
    yPosition += 20f
    regularPaint.textSize = 10f // Ukuran lebih kecil untuk pesan
    val thanksMessage = "Terima kasih Telah Berbelanja di Toko Kami!"
    canvas.drawText(thanksMessage, (paperWidth - regularPaint.measureText(thanksMessage)) / 2, yPosition, regularPaint) // Center

    // Selesai halaman
    pdfDocument.finishPage(page)

    // Menyimpan PDF
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "order_${order.orderId}.pdf")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                val outputStream = resolver.openOutputStream(uri)
                pdfDocument.writeTo(outputStream!!)
                outputStream.close()
                Toast.makeText(context, "PDF berhasil disimpan di folder Downloads.", Toast.LENGTH_SHORT).show()
            }
        } else {
            val filePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "order_${order.orderId}.pdf")
            pdfDocument.writeTo(FileOutputStream(filePath))
            Toast.makeText(context, "PDF berhasil disimpan di folder Downloads.", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Gagal menyimpan PDF: ${e.message}", Toast.LENGTH_SHORT).show()
    } finally {
        pdfDocument.close()
    }
}





