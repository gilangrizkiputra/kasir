package com.project.kasirku.presentation.Admin.Produk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.TitleItem

@Composable
fun DetailProdukScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    DetailProdukContent(navController, modifier)
}

@Composable
fun DetailProdukContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val detailText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("ID Produk : ")
        }
        append("MK001\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Kategori : ")
        }
        append("Makanan\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Nama Produk : ")
        }
        append("Pangsit Bojot\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Merk : ")
        }
        append("Ursweethingsq\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Stok : ")
        }
        append("20\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Harga : ")
        }
        append("Rp. 20.000\n")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Satuan : ")
        }
        append("Pcs\n")
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TitleItem(
            title = "Detail Produk",
            onBackClick = {
                navController.navigate(Screen.Produk.route){
                    popUpTo(Screen.DetailProduk.route) { inclusive = true }
                }
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