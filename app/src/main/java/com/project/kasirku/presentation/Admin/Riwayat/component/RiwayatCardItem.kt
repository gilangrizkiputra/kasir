package com.project.kasirku.presentation.Admin.Riwayat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.kasirku.R

@Composable
fun RiwayatCardItem(
    orderId: String,
    namaPelanggan: String,
    tanggal: String,
    quantity: Int,
    totalHarga: Int,
    keuntungan: Int,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            color = Color.White,
            shadowElevation = 6.dp,
            shape = RoundedCornerShape(10.dp),
            onClick = { onItemClick(orderId) }
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_riwayat) ,
                    contentDescription = "riwayat"
                )
                Spacer(modifier = Modifier.padding(end = 8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row{
                        Text(
                            text = "#${orderId}",
                            fontSize = 11.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_user_riwayat),
                            contentDescription = "user",
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp, bottom = 6.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = namaPelanggan,
                            fontSize = 11.sp
                        )
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp
                    )
                    Row(
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_kalender),
                            contentDescription = "user",
                            modifier = Modifier
                                .padding(top = 1.dp, end = 4.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = tanggal,
                            fontSize = 11.sp
                        )

                    }
                    Text(
                        text = "Jumlah Pesanan : ${quantity} " +
                                "\nTotal Harga : Rp. ${totalHarga} " +
                                "\nTotal Keuntungan : Rp. ${keuntungan}",
                        fontSize = 11.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
}
