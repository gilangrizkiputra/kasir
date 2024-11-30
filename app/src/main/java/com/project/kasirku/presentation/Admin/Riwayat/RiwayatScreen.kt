package com.project.kasirku.presentation.Admin.Riwayat

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Riwayat.component.FormDateItem
import com.project.kasirku.presentation.Admin.Riwayat.component.RiwayatCardItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun RiwayatScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var firstDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    val filteredOrderList = rememberSaveable { mutableStateOf<List<Orders>>(emptyList()) }
    val orderList = remember { mutableStateOf<List<Orders>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }

    val database = FirebaseDatabase.getInstance().getReference("orders")
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderItems = mutableListOf<Orders>()
                for (orderSnapshot in snapshot.children) {
                    val orders = orderSnapshot.getValue(Orders::class.java)
                    if (orders != null) {
                        orderItems.add(orders)
                    }
                }
                orderList.value = orderItems
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                isError.value = true
                isLoading.value = false
            }
        })
    }

    fun filterOrdersByDateRange() {
        if (firstDate.isNotEmpty() && endDate.isNotEmpty()) {
            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss")

            val startDate = LocalDateTime.parse("$firstDate 00:00:00", formatter)
            val endDate = LocalDateTime.parse("$endDate 23:59:59", formatter)

            filteredOrderList.value = orderList.value.filter { order ->
                val orderDate = LocalDateTime.parse(order.tanggal, DateTimeFormatter.ofPattern("EEEE, d/M/yyyy, HH:mm", Locale("id", "ID")))
                (orderDate.isEqual(startDate) || orderDate.isAfter(startDate)) &&
                        (orderDate.isEqual(endDate) || orderDate.isBefore(endDate))
            }
        }
    }

    RiwayatTransaksiContent(
        orderList = filteredOrderList.value,
        isLoading = isLoading.value,
        isError = isError.value,
        firstDate = firstDate,
        endDate = endDate,
        onFirstDateChange = { firstDate = it },
        onEndDateChange = { endDate = it },
        onFilterClick = { filterOrdersByDateRange() },
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun RiwayatTransaksiContent(
    orderList: List<Orders>,
    isLoading: Boolean,
    isError: Boolean,
    firstDate: String,
    endDate: String,
    onFirstDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Periode Transaksi",
            fontSize = 16.sp
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            FormDateItem(
                title = "Tanggal awal",
                value = firstDate,
                onValueChange = onFirstDateChange,
                placeable = "-"
            )
            FormDateItem(
                title = "Tanggal akhir",
                value = endDate,
                onValueChange = onEndDateChange,
                placeable = "-"
            )
        }
        Button(
            onClick = onFilterClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Periksa",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...")
                }
            } else if (isError) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Gagal memuat data transaksi", color = Color.Red)
                }
            } else if (orderList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada transaksi pada rentang tanggal ini", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(orderList) { orders ->
                        orders.orderId?.let {
                            val totalQuantity = orders.items.sumOf { it.quantity }
                            RiwayatCardItem(
                                orderId = orders.orderId,
                                namaPelanggan = orders.namaPelanggan,
                                tanggal = orders.tanggal,
                                quantity = totalQuantity,
                                totalHarga = orders.totalHarga,
                                keuntungan = orders.keuntungan,
                                onItemClick = { orderId ->
                                    navController.navigate(Screen.DetailTransaksi.route + "/${orderId}")
                                }
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    if (orderList.isEmpty()){
                        Toast.makeText(context, "Harap pilih periode transaksi", Toast.LENGTH_SHORT).show()
                    }else{
                        ExportDataToExcel(context, orderList)
                    }
                },
                containerColor = secondary,
                modifier = Modifier
                    .padding(bottom = 64.dp, end = 8.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_print),
                    contentDescription = "cart",
                    tint = Color.White,
                    modifier = Modifier
                )
            }
        }
    }
}