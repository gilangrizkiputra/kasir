package com.project.kasirku.presentation.Kasir.Beranda

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.kasirku.R
import com.project.kasirku.presentation.Kasir.Beranda.component.CustomDialogCheckoutItem
import com.project.kasirku.presentation.Kasir.Beranda.component.ProdukKasirCardItem
import com.project.kasirku.presentation.component.DropdownKategoriProdukItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BerandaKasirScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var cariProduk by remember {
        mutableStateOf("")
    }

    BerandaKasirContent(
        cariProduk = cariProduk,
        onCariProdukChange = { cariProduk = it },
        navController = navController,
        modifier
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerandaKasirContent(
    cariProduk: String,
    onCariProdukChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var showDialogPesan by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }

    if (showDialogPesan){
        CustomDialogCheckoutItem(
            title = "Pesan",
            quantity = quantity,
            deskDialog = "Tambahkan ke keranjang pesanan?",
            namaProduk = "Pangsit Bojot",
            buttonText = "Pesan",
            colorBackground = primary,
            onDismiss = { showDialogPesan = false },
            onConfirm = { showDialogPesan = false },
            onTambah = { quantity++ },
            onKurang = { if (quantity > 1) quantity-- })
    }
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = cariProduk,
                onValueChange = onCariProdukChange,
                placeholder = {
                    Text(
                        text = "Cari Produk",
                        fontSize = 12.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.ExtraLight,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search",
                        tint = Color.Black
                    )
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            DropdownKategoriProdukItem(title = "Kategori")
        }
        LazyVerticalGrid(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Adaptive(140.dp),
            modifier = modifier.fillMaxSize()
        ) {
            item {
                ProdukKasirCardItem(onItemClick = { showDialogPesan = true })
            }
            item {
                ProdukKasirCardItem(onItemClick = { showDialogPesan = true })
            }
        }

    }
}