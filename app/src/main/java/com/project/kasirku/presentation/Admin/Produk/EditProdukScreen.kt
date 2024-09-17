package com.project.kasirku.presentation.Admin.Produk

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Produk.component.DropdownKategoriTambahProdukItem
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun EditProdukScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    var idProduk by remember {
        mutableStateOf("")
    }

    var namaProduk by remember {
        mutableStateOf("")
    }

    var merk by remember {
        mutableStateOf("")
    }

    var stok by remember {
        mutableStateOf("")
    }

    var hargaBeli by remember {
        mutableStateOf("")
    }

    var hargaJual by remember {
        mutableStateOf("")
    }

    var satuan by remember {
        mutableStateOf("")
    }


    EditProdukContent(
        idproduk = idProduk,
        namaProduk = namaProduk,
        merk = merk,
        stok = stok,
        hargaBeli = hargaBeli,
        hargaJual = hargaJual,
        satuan = satuan,
        onIdProdukChange = { idProduk = it },
        onNamaProdukChange = { namaProduk = it },
        onMerkChange = { merk = it },
        onStokChange = { stok = it },
        onHargaBeliChange = { hargaBeli = it },
        onHargaJualChange = { hargaJual = it },
        onSatuanChange = { satuan = it },
        navController,
        modifier
    )
}

@Composable
fun EditProdukContent(
    idproduk: String,
    namaProduk: String,
    merk: String,
    stok: String,
    hargaBeli: String,
    hargaJual: String,
    satuan: String,
    onIdProdukChange: (String) -> Unit,
    onNamaProdukChange: (String) -> Unit,
    onMerkChange: (String) -> Unit,
    onStokChange: (String) -> Unit,
    onHargaBeliChange: (String) -> Unit,
    onHargaJualChange: (String) -> Unit,
    onSatuanChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        TitleItem(
            title = "Edit Produk",
            onBackClick = {
                navController.navigate(Screen.Produk.route){
                    popUpTo(Screen.EditProduk.route) { inclusive = true }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        FormItem(
            title = "Id Produk",
            value = idproduk,
            onValueChange = onIdProdukChange,
            placeable = "Masukkan ID produk",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownKategoriTambahProdukItem(
            title = "Kategori"
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Nama produk",
            value = namaProduk,
            onValueChange = onNamaProdukChange,
            placeable = "Masukkan nama produk",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Merk",
            value = merk,
            onValueChange = onMerkChange,
            placeable = "Masukkan merk",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Stok",
            value = stok,
            onValueChange = onStokChange,
            placeable = "Masukkan stok",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Harga Beli",
            value = hargaBeli,
            onValueChange = onHargaBeliChange,
            placeable = "Masukkan harga beli",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Harga Jual",
            value = hargaJual,
            onValueChange = onHargaJualChange,
            placeable = "Masukkan harga jual",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Satuan",
            value = satuan,
            onValueChange = onSatuanChange,
            placeable = "Masukkan satuan",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Simpan",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}