package com.project.kasirku.presentation.Kasir.Beranda

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.model.CartItem
import com.project.kasirku.model.Kategori
import com.project.kasirku.model.Produk
import com.project.kasirku.presentation.Kasir.Beranda.component.CustomDialogCheckoutItem
import com.project.kasirku.presentation.Kasir.Beranda.component.ProdukKasirCardItem
import com.project.kasirku.presentation.component.DropdownKategoriProdukItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BerandaKasirScreen(
    navController: NavController,
    cartItems: MutableList<CartItem>,
    modifier: Modifier = Modifier
) {
    var cariProduk by remember { mutableStateOf("") }
    val produkList = remember { mutableStateOf<List<Produk>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val isError = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf<Kategori?>(null) }
    val database = FirebaseDatabase.getInstance().getReference("produk")

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
                produkList.value = produkItems
                isLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                isError.value = true
                isLoading.value = false
            }
        })
    }

    BerandaKasirContent(
        cariProduk = cariProduk,
        produkList = produkList.value,
        isLoading = isLoading.value,
        isError = isError.value,
        cartItems = cartItems,
        selectedCategory = selectedCategory.value,
        onKategoriChange = { selectedCategory.value = it },
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
    produkList: List<Produk>,
    isLoading: Boolean,
    isError: Boolean,
    cartItems: MutableList<CartItem>,
    selectedCategory: Kategori?,
    onKategoriChange: (Kategori) -> Unit,
    onCariProdukChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDialogPesan by rememberSaveable { mutableStateOf(false) }
    var selectedProduk by rememberSaveable { mutableStateOf<Produk?>(null) }
    var quantity by remember { mutableStateOf(1) }

    if (showDialogPesan && selectedProduk != null) {
        CustomDialogCheckoutItem(
            title = "Pesan",
            quantity = quantity,
            deskDialog = "Tambahkan ke keranjang pesanan?",
            namaProduk = selectedProduk?.namaProduk ?: "",
            buttonText = "Pesan",
            colorBackground = primary,
            produkImageUrl = selectedProduk?.produkImageUrl ?: "",
            onDismiss = { showDialogPesan = false },
            onConfirm = {
                val existingItem = cartItems.find { it.produk.idProduk == selectedProduk?.idProduk }
                if (existingItem != null) {
                    existingItem.quantity += quantity
                } else {
                    selectedProduk?.let {
                        cartItems.add(CartItem(it, quantity))
                    }
                }
                quantity = 1
                showDialogPesan = false
            },
            onTambah = { quantity++ },
            onKurang = { if (quantity > 1) quantity-- }
        )
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
            DropdownKategoriProdukItem(
                title = "Kategori",
                onKategoriChange = onKategoriChange
            )
        }
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
                LazyVerticalGrid(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    columns = GridCells.Adaptive(140.dp),
                    modifier = modifier.fillMaxSize()
                ) {
                    val filteredProdukList = produkList.filter { produk ->
                        (selectedCategory == null || selectedCategory.name == "All" || produk.kategori == selectedCategory.name) &&
                                produk.namaProduk.contains(cariProduk, ignoreCase = true)
                    }
                    if (filteredProdukList.isEmpty()){
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 250.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = "Produk tidak tersedia", color = Color.Gray,)
                            }
                        }
                    } else {
                        items(filteredProdukList) { produk ->
                            produk.idProduk?.let { idProduk ->
                                ProdukKasirCardItem(
                                    produkName = produk.namaProduk,
                                    hargaJual = produk.hargaJual,
                                    stok = produk.stok,
                                    produkImageUrl = produk.produkImageUrl,
                                    onItemClick = {
                                        selectedProduk = produk
                                        if (produk.stok == 0){
                                            Toast.makeText(context, "Tidak bisa melakukan pemesanan stok produk 0", Toast.LENGTH_SHORT).show()
                                        }else{
                                            showDialogPesan = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}