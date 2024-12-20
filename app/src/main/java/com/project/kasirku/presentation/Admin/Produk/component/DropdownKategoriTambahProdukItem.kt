package com.project.kasirku.presentation.Admin.Produk.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.R
import com.project.kasirku.model.Kategori
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownKategoriTambahProdukItem(
    title: String,
    selectedKategori: String,
    onKategoriChange: (String) -> Unit
) {
    val context = LocalContext.current
    var categories by remember { mutableStateOf<List<Kategori>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Kategori?>(null) }

    val database = FirebaseDatabase.getInstance().getReference("kategori")

    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val kategoriItems = mutableListOf<Kategori>()
                for (kategoriSnapshot in snapshot.children) {
                    val kategori = kategoriSnapshot.getValue(Kategori::class.java) // Mengonversi snapshot menjadi objek Kategori
                    if (kategori != null) {
                        kategoriItems.add(kategori) // Tambahkan ke list kategori
                    }
                }
                categories = kategoriItems // Update state dengan daftar kategori dari Firebase
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Gagal mengambil kategori", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Column(
        modifier = Modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            Surface(
                modifier = Modifier
                    .menuAnchor() // Anchor untuk mengaitkan dropdown dengan trigger
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    ),
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(16.dp)
                ) {
                    Text(
                        text = selectedCategory?.name ?: "Pilih Kategori",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (selectedCategory == null) Color.Gray else Color.Black
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown),
                        contentDescription = "Dropdown Arrow",
                        tint = Color.Black,
                        modifier = Modifier.size(width = 16.dp, height = 8.dp)
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(secondary)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = category.name,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        onClick = {
                            selectedCategory = category
                            onKategoriChange(category.name)
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
