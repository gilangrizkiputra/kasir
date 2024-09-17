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
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.White,
            shadowElevation = 6.dp,
            shape = RoundedCornerShape(10.dp),
            onClick = { onItemClick() }
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
                            text = "#001",
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
                            text = "Asep",
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
                            text = "Kamis, 09/08/2024, 12.00",
                            fontSize = 11.sp
                        )

                    }
                    Text(
                        text = "Jumlah Pesanan : 1 " +
                                "\nTotal Harga : Rp. 24.000 " +
                                "\nTotal Keuntungan : Rp. 4.000",
                        fontSize = 11.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
}
