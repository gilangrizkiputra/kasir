package com.project.kasirku.presentation.Admin.Detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Produk.DetailProdukContent
import com.project.kasirku.presentation.component.TitleItem

@Composable
fun DetailTransaksiScreen(
    orderId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val ordersDetail = remember { mutableStateOf<Orders?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }
    val database = FirebaseDatabase.getInstance().getReference("orders").child(orderId ?: "")

    LaunchedEffect(orderId) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orders = snapshot.getValue(Orders::class.java)
                ordersDetail.value = orders
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
    } else if (ordersDetail.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Produk tidak ditemukan", color = Color.Gray)
        }
    } else {
        DetailTransaksiContent(
            orders = ordersDetail.value!!,
            navController = navController)
    }

}

@Composable
fun DetailTransaksiContent(
    orders: Orders,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TitleItem(
            title = "Detail Transaksi",
            onBackClick = {
                navController.popBackStack()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_kasirku),
                                contentDescription = "logo",
                                modifier = Modifier
                            )
                            Text(
                                text = "Ursweethingsq",
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Bekasi",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Divider(
                                thickness = 1.dp,
                                color = Color.Black
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = orders.tanggal,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Kasir : Ujang",
                                    fontSize = 16.sp
                                )
                            }
                            Divider(
                                thickness = 1.dp,
                                color = Color.Black
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                orders.items.forEach { item ->
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "${item.namaProduk}\n${item.quantity} x ${item.hargaJual}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Rp. ${item.quantity * item.hargaJual}",
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                            Divider(
                                thickness = 1.dp,
                                color = Color.Black
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = "Total :",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Rp. ${orders.totalHarga}",
                                    fontSize = 16.sp
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = "bayar :",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Rp. ${orders.bayar}",
                                    fontSize = 16.sp
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = "kembali :",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Rp. ${orders.kembali}",
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.padding(16.dp))
                            Text(
                                text = "Terima kasih sudah berbelanja",
                                fontSize = 16.sp,
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}