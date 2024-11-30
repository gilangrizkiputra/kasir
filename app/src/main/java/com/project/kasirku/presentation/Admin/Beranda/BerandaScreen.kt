package com.project.kasirku.presentation.Admin.Beranda

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.R
import com.project.kasirku.model.Orders
import com.project.kasirku.model.Produk
import com.project.kasirku.presentation.Admin.Beranda.component.ProdukCardHomeItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun BerandaScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var totalPendapatan by remember { mutableStateOf(0) }
    var jumlahProduk by remember { mutableStateOf(0) }
    var stokProduk by remember { mutableStateOf(0) }
    var produkTerjual by remember { mutableStateOf(0) }
    var jumlahKategori by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        fetchProdukData { produkList ->
            jumlahProduk = produkList.size
            stokProduk = produkList.sumOf { it.stok }
            jumlahKategori = produkList.map { it.kategori }.distinct().size
        }

        fetchOrderData { ordersList ->
            totalPendapatan = ordersList.sumOf { it.totalHarga }
            produkTerjual = ordersList.sumOf { order ->
                order.items.sumOf { it.quantity }
            }
        }
    }

    BerandaScreenContent(
        totalPendapatan = totalPendapatan,
        jumlahProduk = jumlahProduk,
        stokProduk = stokProduk,
        produkTerjual = produkTerjual,
        jumlahKategori = jumlahKategori,
        navController = navController,
        modifier = Modifier
    )
}

@Composable
fun BerandaScreenContent(
    totalPendapatan: Int,
    jumlahProduk: Int,
    stokProduk: Int,
    produkTerjual: Int,
    jumlahKategori: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            color = secondary,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_saldo),
                    contentDescription = "Saldo",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 16.dp)
                )
                Column {
                    Text(
                        text = "Total Pendapatan",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Rp. ${totalPendapatan}",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Produk",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProdukCardHomeItem(
                title = "Jumlah",
                count = "${jumlahProduk}",
                icon = R.drawable.ic_jumlah_produk
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ProdukCardHomeItem(
                title = "Stok",
                count = "${stokProduk}",
                icon = R.drawable.ic_stock_produk
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProdukCardHomeItem(
                title = "Terjual",
                count = "${produkTerjual}",
                icon = R.drawable.ic_produk_terjual
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ProdukCardHomeItem(
                title = "Kategori",
                count = "${jumlahKategori}",
                icon = R.drawable.ic_produk_kategori
            )
        }
    }
}

fun fetchProdukData(onDataFetched: (List<Produk>) -> Unit) {
    val database = FirebaseDatabase.getInstance().getReference("produk")
    database.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val produkList = mutableListOf<Produk>()
            for (data in snapshot.children) {
                val produk = data.getValue(Produk::class.java)
                produk?.let { produkList.add(it) }
            }
            onDataFetched(produkList)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Firebase", "Error fetching produk data: ${error.message}")
        }
    })
}

fun fetchOrderData(onDataFetched: (List<Orders>) -> Unit) {
    val database = FirebaseDatabase.getInstance().getReference("orders")
    database.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val ordersList = mutableListOf<Orders>()
            for (data in snapshot.children) {
                val order = data.getValue(Orders::class.java)
                order?.let { ordersList.add(it) }
            }
            onDataFetched(ordersList)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Firebase", "Error fetching orders data: ${error.message}")
        }
    })
}

