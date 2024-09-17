package com.project.kasirku.presentation.Kasir.Keranjang.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.project.kasirku.R
import com.project.kasirku.ui.theme.KasirKuTheme
import com.project.kasirku.ui.theme.primary

@Composable
fun KeranjangCardItem(
    quantity: Int,
    onTambah: () -> Unit,
    onKurang: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        color = Color.White,
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_pangsit),
                contentDescription = "pangsit bojot",
                modifier = Modifier
                    .width(76.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.padding(start = 8.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pangsit Bojot",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                Text(
                    text = "Rp. 20.000",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Button(
                        onClick = onTambah,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .width(52.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "+",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "$quantity",
                        fontSize = 19.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Button(
                        onClick = onKurang,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .width(52.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "-",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
