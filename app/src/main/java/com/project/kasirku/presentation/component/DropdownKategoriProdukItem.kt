package com.project.kasirku.presentation.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.kasirku.R
import com.project.kasirku.ui.theme.primary

import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.model.Kategori

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownKategoriProdukItem(
    title: String,
    onKategoriChange: (Kategori) -> Unit
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
                val allKategori = Kategori("All", "All")
                kategoriItems.add(allKategori)
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
            .width(173.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
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
                    .fillMaxWidth(),
                color = primary,
                shape = RoundedCornerShape(5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(4.dp)
                ) {
                    Text(
                        text = selectedCategory?.name ?: "Pilih Kategori",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown),
                        contentDescription = "Dropdown Arrow",
                        tint = Color.White,
                        modifier = Modifier.size(width = 16.dp, height = 8.dp)
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(173.dp)
                    .background(primary)
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
                            onKategoriChange(category)
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

