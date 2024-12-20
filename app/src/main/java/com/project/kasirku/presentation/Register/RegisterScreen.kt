//package com.project.kasirku.presentation.Register
//
//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.project.kasirku.navigation.Screen
//import com.project.kasirku.presentation.component.FormInputItem
//import com.project.kasirku.presentation.component.FormInputPasswordItem
//import com.project.kasirku.ui.theme.poppinsFontFamily
//import com.project.kasirku.ui.theme.primary
//import com.project.kasirku.ui.theme.secondary
//
//@Composable
//fun RegistrasiScreen(
//    navController: NavController,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    var username by remember {
//        mutableStateOf("")
//    }
//    var email by remember {
//        mutableStateOf("")
//    }
//    var nomorTelephone by remember {
//        mutableStateOf("")
//    }
//    var alamat by remember {
//        mutableStateOf("")
//    }
//    var password by remember {
//        mutableStateOf("")
//    }
//    var confirmPassword by remember {
//        mutableStateOf("")
//    }
//
//    RegistrasiContent(
//        username = username,
//        email = email,
//        nomorTelephone = nomorTelephone,
//        alamat = alamat,
//        password = password,
//        confirmPassword = confirmPassword,
//        onUsernameChange = { username = it },
//        onEmailChange = { email = it },
//        onNomortelephoneChange = { nomorTelephone = it},
//        onAlamatChange = { alamat = it},
//        onPasswordChange = { password = it },
//        onConfirmpasswordChange = { confirmPassword = it},
//        onDaftarClick = {
//            Toast.makeText(context, "Login Clicked", Toast.LENGTH_SHORT).show()
//        },
//        onMasukClick = {
//                       navController.navigate(Screen.Masuk.route){
//                           popUpTo(Screen.Daftar.route){ inclusive = true }
//                       }
//        },
//        modifier = modifier
//    )
//}
//
//@Composable
//fun RegistrasiContent(
//    username: String,
//    email: String,
//    nomorTelephone: String,
//    alamat: String,
//    password: String,
//    confirmPassword: String,
//    onUsernameChange: (String) -> Unit,
//    onEmailChange: (String) -> Unit,
//    onNomortelephoneChange: (String) -> Unit,
//    onAlamatChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit,
//    onConfirmpasswordChange: (String) -> Unit,
//    onDaftarClick: () -> Unit,
//    onMasukClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var passwordVisible by remember { mutableStateOf(false) }
//    var confirmPasswordVisible by remember { mutableStateOf(false) }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp)
//    ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(20.dp)),
//            color = primary,
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//            ) {
//                Row (
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier.fillMaxWidth()
//                ){
//                    Text(
//                        text = "Daftar",
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 27.sp,
//                        color = secondary
//                    )
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//                FormInputItem(
//                    title = "username",
//                    value = username,
//                    onValueChange = onUsernameChange,
//                    placeable = "Masukkan Username",
//                    keyboardType = KeyboardType.Text
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                FormInputItem(
//                    title = "Email",
//                    value = email,
//                    onValueChange = onEmailChange ,
//                    placeable = "Masukkan Email",
//                    keyboardType = KeyboardType.Email
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                FormInputItem(
//                    title = "Nomor Telephone",
//                    value = nomorTelephone,
//                    onValueChange = onNomortelephoneChange,
//                    placeable = "Masukkan Nomor Telephone",
//                    keyboardType = KeyboardType.Number
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                FormInputItem(
//                    title = "Alamat",
//                    value = alamat,
//                    onValueChange = onAlamatChange,
//                    placeable = "Masukkan Alamat",
//                    keyboardType = KeyboardType.Text
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                FormInputPasswordItem(
//                    title = "Password",
//                    value = password,
//                    onValueChange = onPasswordChange,
//                    placeable = "Masukkan Password",
//                    passwordVisible = passwordVisible,
//                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                FormInputPasswordItem(
//                    title = "Konfirmasi Password",
//                    value = confirmPassword,
//                    onValueChange = onConfirmpasswordChange,
//                    placeable = "Masukkan Konfirmasi Password",
//                    passwordVisible = confirmPasswordVisible,
//                    onPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Row(
//                    horizontalArrangement = Arrangement.End,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Sudah punya akun?",
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 12.sp,
//                        color = Color.White
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "Ya",
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 12.sp,
//                        color = secondary,
//                        modifier = Modifier.clickable {
//                            onMasukClick()
//                        }
//                    )
//                }
//                Spacer(modifier = Modifier.height(24.dp))
//                Button(
//                    onClick = onDaftarClick,
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = secondary
//                    ),
//                    shape = RoundedCornerShape(10.dp),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(48.dp)
//                ) {
//                    Text(
//                        text = "Daftar",
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 16.sp,
//                        color = Color.White
//                    )
//                }
//                Spacer(modifier = Modifier.height(24.dp))
//            }
//        }
//    }
//}
