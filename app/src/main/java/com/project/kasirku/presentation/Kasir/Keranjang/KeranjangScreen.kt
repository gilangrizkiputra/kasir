package com.project.kasirku.presentation.Kasir.Keranjang

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Kasir.Keranjang.component.KeranjangCardItem
import com.project.kasirku.presentation.component.FormInputItem
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.presentation.Kasir.Beranda.BerandaKasirScreen
import com.project.kasirku.presentation.component.CustomDialogFormItem
import com.project.kasirku.presentation.component.CustomDialogItem
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KeranjangScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var  namaPelanggan by remember {
        mutableStateOf("")
    }

    var  catatan by remember {
        mutableStateOf("")
    }

    KeranjangScreenContent(
        namaPelanggan = namaPelanggan,
        catatan = catatan,
        onNamaPelangganChange = { namaPelanggan = it },
        onCatatanChange = { catatan = it },
        navController = navController,
        modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KeranjangScreenContent(
    namaPelanggan: String,
    catatan: String,
    onNamaPelangganChange: (String) -> Unit,
    onCatatanChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var quantity by remember {
        mutableStateOf(1)
    }

    var bayar by remember {
        mutableStateOf("")
    }

    var showDialogBayar by remember { mutableStateOf(false) }
    var showDialogBerhasil by remember { mutableStateOf(false) }


    if (showDialogBerhasil){
        CustomDialogItem(
            title = "Sukses",
            deskDialog = "Pembayaran berhasil, ingin cetak struck?",
            onDismiss = {
                showDialogBerhasil = false
                showDialogBayar = false
                bayar = ""
            },
            onConfirm = {
                showDialogBerhasil = false
                showDialogBayar = false
                bayar = ""
            })
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
                showDialogBerhasil = true
                bayar = ""
            })
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
            title = "Nama Catatan",
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
            modifier = Modifier.fillMaxHeight()
        ) {
            item {
                KeranjangCardItem(
                    quantity = quantity,
                    onTambah = { quantity++ },
                    onKurang = { if (quantity > 1) quantity-- }
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
                    text = "Total : Rp. 20.000",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                    color = Color.White,
                )
                Button(
                    onClick = { showDialogBayar = true },
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
