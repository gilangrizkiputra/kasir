package com.project.kasirku.presentation.Admin.Kategori

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.R
import com.project.kasirku.model.Kategori
import com.project.kasirku.presentation.Admin.Kategori.component.KategoriCardItem
import com.project.kasirku.presentation.component.CustomDialogFormItem
import com.project.kasirku.presentation.component.CustomDialogItem
import com.project.kasirku.presentation.component.getCurrentDate
import com.project.kasirku.ui.theme.secondary
import com.project.kasirku.ui.theme.secondaryEdit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KategoriScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val kategoriList = remember { mutableStateOf<List<Kategori>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }
    val database = FirebaseDatabase.getInstance().getReference("kategori")

    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val kategoriItems = mutableListOf<Kategori>()
                for (kategoriSnapshot in snapshot.children) {
                    val kategori = kategoriSnapshot.getValue(Kategori::class.java)
                    if (kategori != null) {
                        kategoriItems.add(kategori)
                    }
                }
                kategoriList.value = kategoriItems // Update state dengan daftar kategori
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                isError.value = true
                isLoading.value = false
            }
        })
    }
    KategoriContent(
        database = database,
        kategoriList = kategoriList.value,
        isLoading = isLoading.value,
        isError = isError.value,
        navController = navController,
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KategoriContent(
    database: DatabaseReference,
    kategoriList: List<Kategori>,
    isLoading: Boolean,
    isError: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var kategori by remember { mutableStateOf("") }
    var showDialogHapus by remember { mutableStateOf(false) }
    var showDialogEdit by remember { mutableStateOf(false) }
    var showDialogTambah by remember { mutableStateOf(false) }
    var selectedKategoriId by remember { mutableStateOf<String?>(null) }


    fun addKategoriToDatabase(kategoriName: String) {
        val kategoriId = database.push().key
        val createdAt = getCurrentDate()

        val kategoriData = Kategori(id = kategoriId, name = kategoriName, createdAt = createdAt)

        kategoriId?.let {
            database.child(it).setValue(kategoriData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil menambahkan kategori", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Gagal menambahkan kategori", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun updateKategoriInDatabase(kategoriId: String, kategoriName: String) {
        val updatedAt = getCurrentDate()
        val kategoriData = mapOf<String, Any>(
            "name" to kategoriName,
            "updatedAt" to updatedAt
        )

        database.child(kategoriId).updateChildren(kategoriData)
            .addOnSuccessListener {
                Toast.makeText(context, "Kategori berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Gagal memperbarui kategori", Toast.LENGTH_SHORT).show()
            }
    }


    if (showDialogHapus) {
        CustomDialogItem(
            title = "Hapus Kategori",
            deskDialog = "Anda yakin ingin menghapus kategori?",
            onDismiss = { showDialogHapus = false },
            onConfirm = {
                selectedKategoriId?.let { idKategori ->
                    database.child(idKategori).removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Kategori berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Gagal menghapus kategori", Toast.LENGTH_SHORT).show()

                        }
                }
                showDialogHapus = false
            }
        )
    } else if (showDialogEdit) {
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
                selectedKategoriId?.let { idKategori ->
                    if (kategori.isEmpty()) {
                        Toast.makeText(context, "Harap isi nama kategori", Toast.LENGTH_SHORT).show()
                    } else {
                        updateKategoriInDatabase(idKategori, kategori)
                        showDialogEdit = false
                        kategori = ""
                    }
                }
            })
    } else if (showDialogTambah) {
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
                if (kategori.isEmpty()) {
                    Toast.makeText(context, "Harap isi kategori", Toast.LENGTH_SHORT).show()
                }else{
                    addKategoriToDatabase(kategori)
                    showDialogTambah = false
                    kategori = ""
                }
            })
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...")
                }
            } else if (isError) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Gagal memuat data kategori", color = Color.Red)
                }
            } else if (kategoriList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada kategori tersedia", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(kategoriList) { kategoriUpdate ->
                        KategoriCardItem(
                            kategoriName = kategoriUpdate.name,
                            createdAt = kategoriUpdate.createdAt,
                            onHapusClick = {
                                selectedKategoriId = kategoriUpdate.id
                                showDialogHapus = true
                            },
                            onEditClick = {
                                selectedKategoriId = kategoriUpdate.id
                                kategori = kategoriUpdate.name ?: ""
                                showDialogEdit = true
                            },
                        )
                    }
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