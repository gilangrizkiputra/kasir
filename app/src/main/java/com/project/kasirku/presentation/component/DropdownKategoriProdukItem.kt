package com.project.kasirku.presentation.component

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownKategoriProdukItem(
    title: String,
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Makanan") }

    val categories = listOf("Makanan", "Minuman", "Camilan")

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
                        text = selectedCategory,
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
                                text = category,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

