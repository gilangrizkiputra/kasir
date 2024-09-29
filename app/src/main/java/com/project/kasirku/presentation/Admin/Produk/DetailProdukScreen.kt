package com.project.kasirku.presentation.Admin.Produk

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.model.Produk
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.TitleItem

@Composable
fun DetailProdukScreen(
    idProduk: String?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val produkDetail = remember { mutableStateOf<Produk?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }

    val database = FirebaseDatabase.getInstance().getReference("produk").child(idProduk ?: "")

    LaunchedEffect(idProduk) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val produk = snapshot.getValue(Produk::class.java)
                produkDetail.value = produk
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                isError.value = true
                isLoading.value = false
            }
        })
    }

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    } else if (isError.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Gagal memuat data produk", color = Color.Red)
        }
    } else if (produkDetail.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Produk tidak ditemukan", color = Color.Gray)
        }
    } else {
        DetailProdukContent(
            produk = produkDetail.value!!,
            navController = navController,
            modifier = modifier
        )
    }
}

@Composable
fun DetailProdukContent(
    produk: Produk,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val detailText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("ID Produk : ")
        }
        append("${produk.idProduk}\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Kategori : ")
        }
        append("${produk.kategori}\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Nama Produk : ")
        }
        append("${produk.namaProduk}\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Merk : ")
        }
        append("${produk.merk}\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Stok : ")
        }
        append("${produk.stok}\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Harga : ")
        }
        append("Rp. ${produk.hargaJual}\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Satuan : ")
        }
        append("${produk.satuan}\n")
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TitleItem(
            title = "Detail Produk",
            onBackClick = {
                navController.popBackStack()
            }
        )
        Spacer(modifier = modifier.height(40.dp))
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Produk",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = detailText,
                    fontSize = 16.sp
                )
            }
        }
    }
}