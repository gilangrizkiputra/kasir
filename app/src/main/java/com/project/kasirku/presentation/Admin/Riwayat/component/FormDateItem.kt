package com.project.kasirku.presentation.Admin.Riwayat.component

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDateItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeable: String,
) {
    // State untuk menyimpan tanggal
    var selectedDate by remember { mutableStateOf(value) }

    // Mendapatkan context untuk DatePickerDialog
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
        TextField(
            value = selectedDate,
            onValueChange = {},
            placeholder = {
                Text(
                    text = placeable,
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
                    painter = painterResource(id = R.drawable.ic_kalender),
                    contentDescription = "kalender",
                    tint = Color.Black,
                    modifier = Modifier.clickable {
                        datePickerDialog.show()
                    }
                )
            },
            modifier = Modifier
                .height(48.dp)
                .width(170.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color.White),
            singleLine = true,
            readOnly = true
        )
    }
}
