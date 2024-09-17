package com.project.kasirku.presentation.Admin.Kategori.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.kasirku.R
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondaryEdit

@Composable
fun KategoriCardItem(
    onHapusClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Text(
                    text = "Makakan",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = "09/09/2024",
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            Row {
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
                        .clickable { onEditClick() },
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