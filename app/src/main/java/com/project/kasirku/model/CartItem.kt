package com.project.kasirku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val produk: Produk,
    var quantity: Int
): Parcelable
