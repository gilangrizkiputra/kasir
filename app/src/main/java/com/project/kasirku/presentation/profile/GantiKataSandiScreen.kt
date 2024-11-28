package com.project.kasirku.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun GantiKataSandiScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    var passwordLama by remember {
        mutableStateOf("")
    }

    var passwordBaru by remember {
        mutableStateOf("")
    }

    var konfirmPassword by remember {
        mutableStateOf("")
    }

    fun ubahPassword() {

        if (passwordLama.isEmpty() && passwordBaru.isEmpty() && konfirmPassword.isEmpty()){
            Toast.makeText(context, "Form belum lengkap", Toast.LENGTH_SHORT).show()
        }else{
            if (passwordBaru != konfirmPassword) {
                Toast.makeText(context, "Kata sandi baru dan konfirmasi tidak cocok", Toast.LENGTH_SHORT).show()
                return
            }

            currentUser?.let { user ->
                val credential = EmailAuthProvider.getCredential(user.email!!, passwordLama)

                user.reauthenticate(credential)
                    .addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            user.updatePassword(passwordBaru)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        Toast.makeText(context, "Kata sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Screen.Profil.route) {
                                            popUpTo(Screen.GantiKataSandi.route) { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(context, "Gagal mengubah kata sandi: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Kata sandi lama salah", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    GantiKataSandiContent(
        passwordLama = passwordLama,
        passwordBaru = passwordBaru,
        konfirmPassword = konfirmPassword,
        onPasswordLamaChange = { passwordLama = it },
        onPasswordBaruChange = { passwordBaru = it },
        onKonfirmPasswordChange = { konfirmPassword = it },
        onUbahPasswordClick = { ubahPassword() },
        navController,
        modifier
        )

}

@Composable
fun GantiKataSandiContent(
    passwordLama: String,
    passwordBaru: String,
    konfirmPassword: String,
    onPasswordLamaChange: (String) -> Unit,
    onPasswordBaruChange: (String) -> Unit,
    onKonfirmPasswordChange: (String) -> Unit,
    onUbahPasswordClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TitleItem(
            title = "Profile",
            onBackClick = {
                navController.navigate(Screen.Profil.route){
                    popUpTo(Screen.GantiKataSandi.route) { inclusive = true }
                }
            }
        )
        Spacer(modifier = Modifier.height(102.dp))
        FormItem(
            title = "Kata Sandi Lama",
            value = passwordLama,
            onValueChange = onPasswordLamaChange,
            placeable = "Masukkan kata sandi lama",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Kata Sandi Baru",
            value = passwordBaru,
            onValueChange = onPasswordBaruChange,
            placeable = "Masukkan kata sandi Baru",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Konfirmasi Kata Sandi",
            value = konfirmPassword,
            onValueChange = onKonfirmPasswordChange,
            placeable = "Masukkan konfirmasi kata sandi",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onUbahPasswordClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Ubah Password",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}