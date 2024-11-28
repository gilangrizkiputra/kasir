package com.project.kasirku.presentation.Login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.data.DataStorage
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.FormInputItem
import com.project.kasirku.presentation.component.FormInputPasswordItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("user")

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val dataStorage = remember { DataStorage(context) }

    val statusLogin by dataStorage.loginStatus.collectAsState(initial = false)
    val userRole by dataStorage.userRole.collectAsState(initial = null)

    LaunchedEffect(statusLogin, userRole) {
        if (statusLogin){
            if (userRole == "admin") {
                navController.navigate(Screen.Beranda.route) {
                    popUpTo(Screen.Masuk.route) { inclusive = true }
                }
            } else if (userRole == "kasir") {
                navController.navigate(Screen.BerandaKasir.route) {
                    popUpTo(Screen.Masuk.route) { inclusive = true }
                }
            }
        }
    }

    fun LoginDatabase() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        val uid = currentUser?.uid

                        uid?.let {
                            database.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        val role = snapshot.child("role").value as String?
                                        val username = snapshot.child("username").value as String?

                                        if (role != null && username != null) {
                                            coroutineScope.launch {
                                                dataStorage.setLoginStatus(true, email, role)
                                                dataStorage.setUserProfile(username, email)
                                            }
                                            when (role) {
                                                "admin" -> {
                                                    navController.navigate(Screen.Beranda.route) {
                                                        popUpTo(Screen.Masuk.route) { inclusive = true }
                                                    }
                                                    Toast.makeText(context, "Login berhasil sebagai Admin", Toast.LENGTH_SHORT).show()
                                                }
                                                "kasir" -> {
                                                    navController.navigate(Screen.BerandaKasir.route) {
                                                        popUpTo(Screen.Masuk.route) { inclusive = true }
                                                    }
                                                    Toast.makeText(context, "Login berhasil sebagai Kasir", Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    Toast.makeText(context, "Role tidak dikenali", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            Toast.makeText(context, "Data user tidak lengkap", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(context, "Data user tidak ditemukan", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(context, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        } ?: run {
                            Toast.makeText(context, "Login gagal, UID tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Login gagal: Email atau Password salah", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(context, "Email atau Password harus diisi", Toast.LENGTH_SHORT).show()
        }
    }

    LoginScreenContent(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onMasukClick = { LoginDatabase() },
        modifier = modifier
    )
}

@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMasukClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            color = primary,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Masuk",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 27.sp,
                        color = secondary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                FormInputItem(
                    title = "Email",
                    value = email,
                    onValueChange = onEmailChange,
                    placeable = "Masukkan Email",
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormInputPasswordItem(
                    title = "Password",
                    value = password,
                    onValueChange = onPasswordChange,
                    placeable = "Masukkan Password",
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onMasukClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Masuk",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
