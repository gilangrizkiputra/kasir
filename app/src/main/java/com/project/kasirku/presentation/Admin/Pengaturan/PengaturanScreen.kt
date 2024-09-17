package com.project.kasirku.presentation.Admin.Pengaturan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun PengaturanScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    var namaToko by remember {
        mutableStateOf("")
    }

    var alamatToko by remember {
        mutableStateOf("")
    }

    var kontakHp by remember {
        mutableStateOf("")
    }

    var namaPemilik by remember {
        mutableStateOf("")
    }

    PengaturanContent(
        namaToko = namaToko,
        alamatToko = alamatToko,
        kontakHp = kontakHp,
        namaPemilik = namaPemilik,
        onNamaTokoChange = { namaToko = it },
        onAlamatTokoChange = { alamatToko = it },
        onNoHpChange = { kontakHp = it },
        onNamaPemilikChange = { namaPemilik = it },
        navController = navController,
        modifier
    )

}

@Composable
fun PengaturanContent(
    namaToko: String,
    alamatToko: String,
    kontakHp: String,
    namaPemilik: String,
    onNamaTokoChange: (String) -> Unit,
    onAlamatTokoChange: (String) -> Unit,
    onNoHpChange: (String) -> Unit,
    onNamaPemilikChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FormItem(
            title = "Nama Toko",
            value = namaToko,
            onValueChange = onNamaTokoChange,
            placeable = "Masukkan nama toko",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Alamat Toko",
            value = alamatToko,
            onValueChange = onAlamatTokoChange,
            placeable = "Masukkan alamat toko",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Kontak Hp",
            value = kontakHp,
            onValueChange = onNoHpChange,
            placeable = "Masukkan nomor hp",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Nama Pemilik Toko",
            value = namaPemilik,
            onValueChange = onNamaPemilikChange,
            placeable = "Masukkan nama pemilik toko",
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