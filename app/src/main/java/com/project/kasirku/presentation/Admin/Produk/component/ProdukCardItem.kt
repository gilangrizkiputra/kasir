package com.project.kasirku.presentation.Admin.Produk.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.project.kasirku.R
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondaryEdit

@Composable
fun ProdukCardItem(
    produkName: String,
    hargaJual: String,
    stok: String,
    produkImageUrl: String?,
    idProduk: String,
    onItemClick: (String) -> Unit,
    onHapusClick: () -> Unit,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { onItemClick(idProduk) },
        color = Color.White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = produkImageUrl ?: R.drawable.background_image_produk_card
                ),
                contentScale = ContentScale.Crop,
                contentDescription = "Gambar Produk",
                modifier = Modifier
                    .size(width = 65.dp, height = 80.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(modifier = Modifier.padding(start = 8.dp))
            Box(
                modifier = Modifier,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = produkName,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Rp. $hargaJual",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Stok : $stok",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .clickable { onHapusClick() },
                        color = primary
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_hapus),
                            contentDescription = "delete",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Surface(
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .clickable { onEditClick(idProduk) },
                        color = secondaryEdit
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit_produk),
                            contentDescription = "edit",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
