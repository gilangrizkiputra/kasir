package com.project.kasirku.presentation.Admin.Riwayat.component

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.kasirku.R
import com.project.kasirku.ui.theme.poppinsFontFamily
import java.util.*

@Composable
fun FormDateItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeable: String,
) {
    var selectedDate by remember { mutableStateOf(value) }
    val context = LocalContext.current

    // Inisialisasi Calendar dengan tanggal sekarang
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Membuat DatePickerDialog dengan tema kustom
    val datePickerDialog = DatePickerDialog(
        ContextThemeWrapper(context, R.style.Theme_KasirKu), // Menerapkan tema kustom
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            // Ketika tanggal dipilih, perbarui state
            selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            onValueChange(selectedDate) // Panggil callback untuk memberikan value baru
        },
        year, month, day
    )

    Column(
        modifier = Modifier
    ) {
        Text(
            text = title,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .height(48.dp)
                .width(170.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { datePickerDialog.show() }
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedDate.isNotEmpty()) selectedDate else placeable,
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = if (selectedDate.isNotEmpty()) Color.Black else Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_kalender),
                    contentDescription = "kalender",
                    tint = Color.Black
                )
            }
        }
    }
}

