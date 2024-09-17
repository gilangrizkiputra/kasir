package com.project.kasirku.presentation.Admin.Beranda

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
import com.project.kasirku.R
import com.project.kasirku.presentation.Admin.Beranda.component.ProdukCardHomeItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun BerandaScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BerandaScreenContent(
        navController = navController,
        modifier = Modifier
    )
}

@Composable
fun BerandaScreenContent(
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
                        text = "Total Pendapatan bulan ini",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Rp. 500.000",
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
                count = "10",
                icon = R.drawable.ic_jumlah_produk
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ProdukCardHomeItem(
                title = "Stok",
                count = "10",
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
                count = "10",
                icon = R.drawable.ic_produk_terjual
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ProdukCardHomeItem(
                title = "Kategori",
                count = "10",
                icon = R.drawable.ic_produk_kategori
            )
        }
    }
}



