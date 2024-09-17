package com.project.kasirku.presentation.Admin.Produk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Produk.component.ProdukCardItem
import com.project.kasirku.presentation.component.CustomDialogItem
import com.project.kasirku.presentation.component.DropdownKategoriProdukItem
import com.project.kasirku.ui.theme.secondary

@Composable
fun ProdukScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ProdukContent(navController, modifier)
}

@Composable
fun ProdukContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }

   if (showDialog){
       CustomDialogItem(
           title = "Hapus Produk",
           deskDialog = "Anda yakin ingin menghapus produk?",
           onDismiss = { showDialog = false },
           onConfirm = { showDialog = false }
       )
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        DropdownKategoriProdukItem(
            title = "Pilih Kategori Produk"
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    ProdukCardItem(
                        onItemClick = {
                            navController.navigate(Screen.DetailProduk.route){
                                popUpTo(Screen.Produk.route) { inclusive = true }
                            }
                        },
                        onHapusClick = {
                            showDialog = true
                        },
                        onEditClick = {
                                navController.navigate(Screen.EditProduk.route){
                                popUpTo(Screen.Produk.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.TambahProduk.route){
                        popUpTo(Screen.Produk.route) { inclusive = true }
                    }
                },
                containerColor = secondary,
                modifier = Modifier
                    .padding(bottom = 64.dp, end = 8.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_fab),
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}