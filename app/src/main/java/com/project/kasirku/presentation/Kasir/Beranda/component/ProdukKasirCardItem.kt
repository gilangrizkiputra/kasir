package com.project.kasirku.presentation.Kasir.Beranda.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.kasirku.R
import com.project.kasirku.ui.theme.KasirKuTheme

@Composable
fun ProdukKasirCardItem(
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
                painter = painterResource(id = R.drawable.image_pangsit),
                contentDescription = "pangsit bojot",
                modifier = Modifier
                    .height(114.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Pangsit Bojot",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = "Rp. 20.000",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProdukKasir() {
    KasirKuTheme {
        ProdukKasirCardItem(onItemClick = { /*TODO*/ })
    }
    
}