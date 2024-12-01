package com.project.kasirku.presentation.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.project.kasirku.R

@Composable
fun CustomDialogFormItem(
    title: String,
    deskDialog: String,
    buttonText: String,
    colorBackground: Color,
    titleForm: String,
    placeable: String,
    kategori: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    val currentDate = getCurrentDate()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(45.dp),
                color = colorBackground,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_exit),
                        contentDescription = "exit",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(15.dp)
                            .clickable {
                                onDismiss()
                            }
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 56.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = deskDialog,
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = currentDate,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                FormItem(
                    title = titleForm,
                    value = kategori,
                    onValueChange = onValueChange,
                    placeable = placeable,
                    keyboardType = keyboardType
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBackground
                    ),
                    modifier = Modifier
                        .height(40.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = buttonText,
                        color = Color.White
                    )
                }
            }
        }
    }
}