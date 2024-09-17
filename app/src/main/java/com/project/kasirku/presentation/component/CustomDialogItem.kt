package com.project.kasirku.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.project.kasirku.R
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondaryEdit


@Composable
fun CustomDialogItem(
    title: String,
    deskDialog: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                color = primary,
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 56.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = deskDialog,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "Batal",
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryEdit
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .padding(start = 8.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "Ya",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}