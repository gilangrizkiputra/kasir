package com.project.kasirku.presentation.Admin.Kategori

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Kategori.component.KategoriCardItem
import com.project.kasirku.presentation.Admin.Produk.component.ProdukCardItem
import com.project.kasirku.presentation.component.CustomDialogFormItem
import com.project.kasirku.presentation.component.CustomDialogItem
import com.project.kasirku.ui.theme.secondary
import com.project.kasirku.ui.theme.secondaryEdit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KategoriScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    KategoriContent(navController, modifier)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KategoriContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var kategori by remember {
        mutableStateOf("")
    }

    var showDialogHapus by remember { mutableStateOf(false) }
    var showDialogEdit by remember { mutableStateOf(false) }
    var showDialogTambah by remember { mutableStateOf(false) }

    if (showDialogHapus){
        CustomDialogItem(
            title = "Hapus Kategori",
            deskDialog = "Anda yakin ingin menghapus kategori?",
            onDismiss = { showDialogHapus = false },
            onConfirm = { showDialogHapus = false }
        )
    }else if (showDialogEdit){
        CustomDialogFormItem(
            title = "Edit Kategori",
            deskDialog = "Tanggal buat",
            buttonText = "Simpan",
            colorBackground = secondaryEdit,
            titleForm = "Kategori",
            placeable = "Masukkan Kategori",
            kategori = kategori,
            onValueChange = { kategori = it },
            onDismiss = {
                showDialogEdit = false
                kategori = ""
            },
            onConfirm = {
                showDialogEdit = false
                kategori = ""
            })
    }else if (showDialogTambah){
        CustomDialogFormItem(
            title = "Tambah Kategori",
            deskDialog = "Tanggal buat",
            buttonText = "Tambah",
            colorBackground = secondary,
            titleForm = "Kategori",
            placeable = "Masukkan Kategori",
            kategori = kategori,
            onValueChange = { kategori = it },
            onDismiss = {
                showDialogTambah = false
                kategori = ""
            },
            onConfirm = {
                showDialogTambah = false
                kategori = ""
            })
        }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    KategoriCardItem(
                        onHapusClick = {
                            showDialogHapus = true
                        },
                        onEditClick = {
                            showDialogEdit = true
                        }
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    showDialogTambah = true
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