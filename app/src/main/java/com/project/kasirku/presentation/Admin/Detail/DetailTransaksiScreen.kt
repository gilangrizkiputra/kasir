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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.TitleItem

data class TransactionItem(
    val name: String,
    val quantity: Int,
    val price: Int
)

@Composable
fun DetailTransaksiScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        TransactionItem("Pangsit Bojot", 1, 20000),
        TransactionItem("Es Teh", 2, 5000),
        TransactionItem("Mie Goreng", 1, 15000),
    )

    DetailTransaksiContent(navController, items = items, modifier)
}

@Composable
fun DetailTransaksiContent(
    navController: NavController,
    items: List<TransactionItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TitleItem(
            title = "Detail Transaksi",
            onBackClick = {
                navController.navigate(Screen.Riwayat.route){
                    popUpTo(Screen.DetailTransaksi.route){ inclusive = true }
                }
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
                                    text = "Kamis, 09/08/2024, 12.00",
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
                                items.forEach { item ->
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "${item.name}\n${item.quantity} x ${item.price}",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "Rp. ${item.quantity * item.price}",
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
                                    text = "Rp. ${items.sumOf { it.quantity * it.price }}",
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
                                    text = "Rp. ${items.sumOf { it.quantity * it.price }}",
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
                                    text = "Rp. 0",
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