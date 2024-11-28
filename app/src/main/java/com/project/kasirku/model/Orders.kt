package com.project.kasirku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Orders(
    val orderId: String = "",
    val catatan: String = "",
    val items: List<Items> = listOf(),
    val namaPelanggan: String = "",
    val kasir: String = "",
    val tanggal: String = "",
    val totalHarga: Int = 0,
    val bayar: Int = 0,
    val kembali: Int = 0,
    val keuntungan: Int = 0
): Parcelable
