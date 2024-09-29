package com.project.kasirku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Items(
    val idProduk: String = "",
    val namaProduk: String = "",
    val hargaJual: Int = 0,
    val quantity: Int = 0
): Parcelable
