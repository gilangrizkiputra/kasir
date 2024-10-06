package com.project.kasirku.presentation.Kasir.Keranjang

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.kasirku.model.CartItem
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Kasir.Keranjang.component.KeranjangCardItem
import com.project.kasirku.data.fetchOrderById
import com.project.kasirku.presentation.Kasir.generatePdf
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.presentation.component.CustomDialogFormItem
import com.project.kasirku.presentation.component.CustomDialogItem
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary

@Composable
fun KeranjangScreen(
    navController: NavController,
    cartItems: MutableList<CartItem>,
    modifier: Modifier = Modifier
) {

    var namaPelanggan by rememberSaveable { mutableStateOf("") }
    var catatan by rememberSaveable { mutableStateOf("") }

    KeranjangScreenContent(
        namaPelanggan = namaPelanggan,
        catatan = catatan,
        cartItems = cartItems,
        onNamaPelangganChange = { namaPelanggan = it },
        onCatatanChange = { catatan = it },
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun KeranjangScreenContent(
    namaPelanggan: String,
    catatan: String,
    cartItems: MutableList<CartItem>,
    onNamaPelangganChange: (String) -> Unit,
    onCatatanChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var bayar by remember { mutableStateOf("") }
    var showDialogBayar by remember { mutableStateOf(false) }
    var showDialogBerhasil by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val totalHarga = cartItems.sumOf { it.produk.hargaJual * it.quantity }
    val keuntungan = cartItems.sumOf { it.produk.hargaBeli * it.quantity }

    // Tambahkan variabel orderId untuk menyimpan ID order yang berhasil
    var orderId by remember { mutableStateOf<String?>(null) }

    if (showDialogBerhasil){
        CustomDialogItem(
            title = "Sukses",
            deskDialog = "Pembayaran berhasil, ingin cetak struck?",
            onDismiss = {
                showDialogBerhasil = false
                showDialogBayar = false
                bayar = ""
                navController.navigate(Screen.BerandaKasir.route)
            },
            onConfirm = {
                // Generate PDF hanya jika orderId tidak null
                orderId?.let { id ->
                    fetchOrderById(id) { fetchedOrder ->
                        if (fetchedOrder != null) {
                            generatePdf(fetchedOrder, context)
                            Toast.makeText(context, "PDF berhasil dibuat!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Order tidak ditemukan.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                // Navigasi setelah PDF berhasil digenerate
                showDialogBerhasil = false
                showDialogBayar = false
                bayar = ""
                navController.navigate(Screen.BerandaKasir.route)
            }
        )
    }else if (showDialogBayar) {
        CustomDialogFormItem(
            title = "Bayar",
            deskDialog = "Tanggal Bayar",
            buttonText = "Bayar",
            colorBackground = primary,
            titleForm = "Tunai",
            placeable = "Input Tunai",
            kategori = bayar,
            onValueChange = { bayar = it },
            onDismiss = {
                showDialogBayar = false
                bayar = ""
            },
            onConfirm = {
                val bayarAmount = bayar.toIntOrNull() ?: 0
                if (bayarAmount >= totalHarga) {
                    saveOrderToFirebase(
                        namaPelanggan = namaPelanggan,
                        catatan = catatan,
                        totalHarga = totalHarga,
                        bayar = bayarAmount,
                        kembali = (bayarAmount - totalHarga),
                        keuntungan = keuntungan,
                        cartItems = cartItems,
                        onSuccess = { newOrderId ->
                            // Simpan orderId setelah pesanan berhasil
                            orderId = newOrderId
                            showDialogBerhasil = true
                            bayar = ""
                            cartItems.clear()
                        },
                        onFailure = { error ->
                            Toast.makeText(context, "Gagal melakukan pemesanan: $error", Toast.LENGTH_SHORT).show()
                            bayar = ""
                        }
                    )
                } else {
                    errorMessage = "Jumlah pembayaran tidak cukup!"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }


    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TitleItem(
            title = "Pesanan",
            onBackClick = { navController.navigate(Screen.BerandaKasir.route){
                popUpTo(Screen.Keranjang.route) { inclusive = true }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Data Pelanggan",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        FormItem(
            title = "Nama Pelanggan",
            value = namaPelanggan,
            onValueChange = onNamaPelangganChange,
            placeable = "Masukkan nama Pelanggan",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Catatan",
            value = catatan,
            onValueChange = onCatatanChange,
            placeable = "Masukkan catatan",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "List Pesanan",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        LazyColumn(
            modifier = Modifier.fillMaxHeight().padding(bottom = 90.dp)
        ) {
            items(cartItems) { cartItem ->
                KeranjangCardItem(
                    produkName = cartItem.produk.namaProduk,
                    quantity = cartItem.quantity,
                    hargaJual = cartItem.produk.hargaJual,
                    produkImageUrl = cartItem.produk.produkImageUrl,
                    onTambah = {
                        val updatedItem = cartItem.copy(quantity = cartItem.quantity + 1)
                        cartItems[cartItems.indexOf(cartItem)] = updatedItem
                    },
                    onKurang = {
                        if (cartItem.quantity > 1) {
                            val updatedItem = cartItem.copy(quantity = cartItem.quantity - 1)
                            cartItems[cartItems.indexOf(cartItem)] = updatedItem
                        }
                    }
                )
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(),
            color = primary,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Total : Rp. ${totalHarga}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    color = Color.White,
                )
                Button(
                    onClick = {
                        if (namaPelanggan.isEmpty() || catatan.isEmpty()){
                            Toast.makeText(context, "Nama pelanggan dan catatan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }else{
                            showDialogBayar = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Bayar",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
