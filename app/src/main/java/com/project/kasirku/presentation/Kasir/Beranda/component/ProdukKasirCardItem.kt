package com.project.kasirku.presentation.Kasir.Beranda.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.project.kasirku.R
import com.project.kasirku.ui.theme.KasirKuTheme

@Composable
fun ProdukKasirCardItem(
    produkName: String,
    hargaJual: Int,
    stok: Int,
    produkImageUrl: String?,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(154.dp)
            .clickable { onItemClick() },
        color = Color.White,
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(
                    model = produkImageUrl ?: R.drawable.background_image_produk_card
                ),
                contentDescription = "Gambar Produk",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop

            )
            Text(
                text = produkName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, end = 4.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = "Rp. ${hargaJual}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
                Text(
                    text = "Stock : ${stok}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 9.sp,
                    color = Color.Gray,
                )
            }
        }
    }
}