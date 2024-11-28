package com.project.kasirku.presentation.Admin.Produk

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.R
import com.project.kasirku.model.Kategori
import com.project.kasirku.model.Produk
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Produk.component.ProdukCardItem
import com.project.kasirku.presentation.component.CustomDialogItem
import com.project.kasirku.presentation.component.DropdownKategoriProdukItem
import com.project.kasirku.presentation.component.getCurrentDate
import com.project.kasirku.ui.theme.secondary

@Composable
fun ProdukScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val produkList = remember { mutableStateOf<List<Produk>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf<Kategori?>(null) }
    val database = FirebaseDatabase.getInstance().getReference("produk")

    // Mengambil data produk dari Firebase
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val produkItems = mutableListOf<Produk>()
                for (produkSnapshot in snapshot.children) {
                    val produk = produkSnapshot.getValue(Produk::class.java)
                    if (produk != null) {
                        produkItems.add(produk)
                    }
                }
                produkList.value = produkItems // Update state dengan daftar produk
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                isError.value = true
                isLoading.value = false
            }
        })
    }

    ProdukContent(
        produkList = produkList.value,
        isLoading = isLoading.value,
        isError = isError.value,
        selectedCategory = selectedCategory.value,
        database = database,
        onKategoriChange = { selectedCategory.value = it },
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun ProdukContent(
    produkList: List<Produk>,
    isLoading: Boolean,
    isError: Boolean,
    selectedCategory: Kategori?,
    database: DatabaseReference,
    onKategoriChange: (Kategori) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var showDialog by remember { mutableStateOf(false) }
    var selectedProdukId by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

   if (showDialog){
       CustomDialogItem(
           title = "Hapus Produk",
           deskDialog = "Anda yakin ingin menghapus produk?",
           onDismiss = { showDialog = false },
           onConfirm = {
               selectedProdukId?.let { idProduk ->
                   database.child(idProduk).removeValue()
                       .addOnSuccessListener {
                           Toast.makeText(context, "Produk berhasil dihapus", Toast.LENGTH_SHORT).show()
                       }
                       .addOnFailureListener {
                           Toast.makeText(context, "Gagal menghapus produk", Toast.LENGTH_SHORT).show()

                       }
               }
               showDialog = false
           }
       )
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        DropdownKategoriProdukItem(
            title = "Pilih Kategori Produk",
            onKategoriChange = onKategoriChange
        )
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
                    Text("Gagal memuat data produk", color = Color.Red)
                }
            } else if (produkList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada produk tersedia", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val filteredProdukList = if (selectedCategory != null && selectedCategory.name != "All") {
                        produkList.filter { it.kategori == selectedCategory.name }
                    } else {
                        produkList
                    }
                    if (filteredProdukList.isEmpty()){
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(top = 250.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = "Produk tidak tersedia", color = Color.Gray,)
                            }
                        }
                    } else {
                        items(filteredProdukList) { produk ->
                            produk.idProduk?.let { idProduk ->
                                ProdukCardItem(
                                    produkName = produk.namaProduk,
                                    hargaJual = produk.hargaJual.toString(),
                                    stok = produk.stok.toString(),
                                    produkImageUrl = produk.produkImageUrl,
                                    idProduk = idProduk,
                                    onItemClick = { selectedId ->
                                        navController.navigate(Screen.DetailProduk.route + "/$selectedId")
                                    },
                                    onHapusClick = {
                                        selectedProdukId = idProduk
                                        showDialog = true
                                    },
                                    onEditClick = {
                                        navController.navigate(Screen.EditProduk.route + "/$idProduk") {
                                            popUpTo(Screen.Produk.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                    }
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