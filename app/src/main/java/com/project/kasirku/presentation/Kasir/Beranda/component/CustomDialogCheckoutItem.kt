package com.project.kasirku.presentation.Kasir.Beranda.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.project.kasirku.R
import com.project.kasirku.ui.theme.KasirKuTheme
import com.project.kasirku.ui.theme.primary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDialogCheckoutItem(
    title: String,
    quantity: Int,
    deskDialog: String,
    namaProduk: String,
    buttonText: String,
    colorBackground: Color,
    produkImageUrl: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onTambah: () -> Unit,
    onKurang: () -> Unit,
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
                    text = namaProduk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black,
                )
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ){
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = produkImageUrl ?: R.drawable.background_image_produk_card
                        ),
                        contentDescription = "Gambar Produk",
                        modifier = Modifier
                            .width(71.dp)
                            .height(65.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop

                    )
                    Button(
                        onClick = onTambah,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorBackground
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .width(52.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "+",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "$quantity",
                        fontSize = 19.sp
                    )
                    Button(
                        onClick = onKurang,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorBackground
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .width(52.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "-",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBackground
                    ),
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
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
