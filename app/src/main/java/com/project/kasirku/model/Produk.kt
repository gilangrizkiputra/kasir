package com.project.kasirku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produk(
    var idProduk: String? = null,
    var namaProduk: String = "",
    var merk: String = "",
    var kategori: String = "",
    var stok: Int = 0,
    var hargaBeli: Int = 0,
    var hargaJual: Int = 0,
    var satuan: String = "",
    var produkImageUrl: String = ""
): Parcelable


