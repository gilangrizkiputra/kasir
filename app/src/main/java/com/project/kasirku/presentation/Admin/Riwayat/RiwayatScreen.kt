package com.project.kasirku.presentation.Admin.Riwayat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Riwayat.component.FormDateItem
import com.project.kasirku.presentation.Admin.Riwayat.component.RiwayatCardItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun RiwayatScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var firstDate by remember {
        mutableStateOf("")
    }

    var endDate by remember {
        mutableStateOf("")
    }

    RiwayatTransaksiContent(
        firstDate = firstDate,
        endDate = endDate,
        onFirstDateChange = { firstDate = it },
        onEndDateChange = { endDate = it },
        navController = navController,
        modifier
    )
}

@Composable
fun RiwayatTransaksiContent(
    firstDate: String,
    endDate: String,
    onFirstDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Riwayat Transaki",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            FormDateItem(
                title = "Tanggal awal",
                value = firstDate,
                onValueChange = onFirstDateChange,
                placeable = "10-08-2024"
            )
            FormDateItem(
                title = "Tanggal akhir",
                value = endDate,
                onValueChange = onEndDateChange,
                placeable = "31-08-2024"
            )
        }
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Periksa",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                RiwayatCardItem(
                    onItemClick = {
                        navController.navigate(Screen.DetailTransaksi.route)
                    }
                )
            }
        }
    }
}