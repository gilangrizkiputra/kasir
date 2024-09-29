package com.project.kasirku.presentation.Admin.Riwayat

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.project.kasirku.model.Orders
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun ExportDataToExcel(context: Context, orders: List<Orders>) {
    // Membuat Workbook
    val workbook: Workbook = XSSFWorkbook()

    // Membuat Sheet
    val sheet = workbook.createSheet("Riwayat Transaksi")

    // Membuat CellStyle untuk border dan alignment
    val cellStyle: CellStyle = workbook.createCellStyle()
    cellStyle.borderTop = BorderStyle.THIN
    cellStyle.borderBottom = BorderStyle.THIN
    cellStyle.borderLeft = BorderStyle.THIN
    cellStyle.borderRight = BorderStyle.THIN
    cellStyle.topBorderColor = IndexedColors.BLACK.index
    cellStyle.bottomBorderColor = IndexedColors.BLACK.index
    cellStyle.leftBorderColor = IndexedColors.BLACK.index
    cellStyle.rightBorderColor = IndexedColors.BLACK.index

    // Set alignment center horizontally and vertically
    cellStyle.alignment = HorizontalAlignment.CENTER
    cellStyle.verticalAlignment = VerticalAlignment.CENTER

    // Buat header kolom dan terapkan border dan centering
    val header = sheet.createRow(0)
    val headers = listOf(
        "Order ID", "Nama Pelanggan", "Tanggal",
        "Produk Dipesan", "Harga Produk", "Quantity",
        "Total Harga", "Keuntungan"
    )
    for (i in headers.indices) {
        val cell = header.createCell(i)
        cell.setCellValue(headers[i])
        cell.cellStyle = cellStyle  // Terapkan border dan center pada header
    }

    // Variabel untuk menyimpan total keuntungan
    var totalKeuntungan = 0.0

    // Isi data transaksi ke dalam sheet
    var rowIndex = 1
    for (order in orders) {
        val startRow = rowIndex  // Baris awal untuk merge cell

        // Menambahkan produk yang dipesan
        if (order.items.isNotEmpty()) {
            for ((index, item) in order.items.withIndex()) {
                val productRow = sheet.createRow(rowIndex + index)

                // **Bagian Produk Dipesan**
                val cellNamaProduk = productRow.createCell(3)
                cellNamaProduk.setCellValue(item.namaProduk)
                cellNamaProduk.cellStyle = cellStyle  // Terapkan border dan center

                // **Bagian Harga Produk**
                val cellHargaProduk = productRow.createCell(4)
                cellHargaProduk.setCellValue(item.hargaJual.toDouble())
                cellHargaProduk.cellStyle = cellStyle  // Terapkan border dan center

                // **Bagian Quantity**
                val cellQuantity = productRow.createCell(5)
                cellQuantity.setCellValue(item.quantity.toDouble())
                cellQuantity.cellStyle = cellStyle  // Terapkan border dan center

                // Terapkan border dan center ke semua sel di baris yang digabung
                for (i in listOf(0, 1, 2, 6, 7)) {
                    val cell = productRow.createCell(i)
                    cell.cellStyle = cellStyle  // Terapkan border dan center pada sel yang digabung
                }
            }

            // Menggabungkan sel untuk "Order ID", "Nama Pelanggan", "Tanggal", "Total Harga", dan "Keuntungan"
            if (order.items.size > 1) {
                sheet.addMergedRegion(CellRangeAddress(startRow, rowIndex + order.items.size - 1, 0, 0))  // Order ID
                sheet.addMergedRegion(CellRangeAddress(startRow, rowIndex + order.items.size - 1, 1, 1))  // Nama Pelanggan
                sheet.addMergedRegion(CellRangeAddress(startRow, rowIndex + order.items.size - 1, 2, 2))  // Tanggal
                sheet.addMergedRegion(CellRangeAddress(startRow, rowIndex + order.items.size - 1, 6, 6))  // Total Harga
                sheet.addMergedRegion(CellRangeAddress(startRow, rowIndex + order.items.size - 1, 7, 7))  // Keuntungan
            }

            // Mengisi data "Order ID", "Nama Pelanggan", "Tanggal", "Total Harga", dan "Keuntungan" di baris pertama saja
            val row = sheet.getRow(startRow)

            // **Bagian Order ID**
            val cellOrderId = row.createCell(0)
            cellOrderId.setCellValue(order.orderId)  // Atur Order ID
            cellOrderId.cellStyle = cellStyle        // Terapkan border dan center pada Order ID

            // **Bagian Nama Pelanggan**
            val cellNamaPelanggan = row.createCell(1)
            cellNamaPelanggan.setCellValue(order.namaPelanggan)
            cellNamaPelanggan.cellStyle = cellStyle  // Terapkan border dan center pada Nama Pelanggan

            // **Bagian Tanggal**
            val cellTanggal = row.createCell(2)
            cellTanggal.setCellValue(order.tanggal)
            cellTanggal.cellStyle = cellStyle  // Terapkan border dan center pada Tanggal

            // **Bagian Total Harga**
            val cellTotalHarga = row.createCell(6)
            cellTotalHarga.setCellValue(order.totalHarga.toDouble())
            cellTotalHarga.cellStyle = cellStyle  // Terapkan border dan center pada Total Harga

            // **Bagian Keuntungan** (hanya tambahkan di baris pertama)
            val cellKeuntungan = row.createCell(7)
            cellKeuntungan.setCellValue(order.keuntungan.toDouble())
            cellKeuntungan.cellStyle = cellStyle  // Terapkan border dan center pada Keuntungan

            // Tambahkan keuntungan ke totalKeuntungan hanya sekali per Order
            totalKeuntungan += order.keuntungan.toDouble()

            // Pindahkan rowIndex sesuai dengan jumlah item
            rowIndex += order.items.size
        } else {
            rowIndex++
        }
    }

    // Menambahkan baris baru untuk total keuntungan di baris terakhir
    val totalRow = sheet.createRow(rowIndex)
    val totalCell = totalRow.createCell(6)  // Menempatkan di kolom "Total Harga"
    totalCell.setCellValue("Total Keuntungan:")
    totalCell.cellStyle = cellStyle  // Terapkan border dan center

    // Menambahkan nilai total keuntungan di kolom "Keuntungan"
    val totalKeuntunganCell = totalRow.createCell(7)  // Menempatkan di kolom "Keuntungan"
    totalKeuntunganCell.setCellValue(totalKeuntungan)
    totalKeuntunganCell.cellStyle = cellStyle  // Terapkan border dan center

    // Mengatur ukuran kolom secara manual
    for (i in headers.indices) {
        sheet.setColumnWidth(i, 4000)
    }

    // **Metode Menyimpan ke Folder Downloads untuk Android 9 (API 28) dan lebih lama**
    val filePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "riwayat_transaksi.xlsx")

    try {
        val fileOutputStream = FileOutputStream(filePath)
        workbook.write(fileOutputStream)
        fileOutputStream.close()
        workbook.close()

        // Beritahu user jika file sudah berhasil disimpan
        Toast.makeText(context, "File berhasil disimpan di: ${filePath.absolutePath}", Toast.LENGTH_SHORT).show()
        println("File berhasil disimpan di: ${filePath.absolutePath}")
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Gagal menyimpan file", Toast.LENGTH_SHORT).show()
        println("Gagal menyimpan file")
    }
}











