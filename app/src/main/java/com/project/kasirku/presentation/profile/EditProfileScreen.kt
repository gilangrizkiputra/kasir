package com.project.kasirku.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.secondary

@Composable
fun EditProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var telephone by remember {
        mutableStateOf("")
    }

    var alamat by remember {
        mutableStateOf("")
    }


    EditProfileScreenContent(
        username = username ,
        email = email,
        telephone = telephone,
        alamat = alamat,
        onUsernameChange = { username = it },
        onEmailChange = { email = it },
        onTelephoneChange = { telephone = it },
        onAlamatChange = { alamat = it },
        onSimpanClick = { /*TODO*/ },
        navController,
        modifier = modifier
    )
}

@Composable
fun EditProfileScreenContent(
    username: String,
    email: String,
    telephone: String,
    alamat: String,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onTelephoneChange: (String) -> Unit,
    onAlamatChange: (String) -> Unit,
    onSimpanClick: () -> Unit,
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
                    popUpTo(Screen.EditProfil.route) { inclusive = true }
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_profil),
                contentDescription = "profile",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Ganti Kata Sandi",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.clickable {
                    TODO()
                }
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        FormItem(
            title = "Username",
            value = username,
            onValueChange = onUsernameChange,
            placeable = "Anonim",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Email",
            value = email,
            onValueChange = onEmailChange,
            placeable = "Example@gmail.com",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Telephone",
            value = telephone,
            onValueChange = onTelephoneChange,
            placeable = "04830948324",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Alamat",
            value = alamat,
            onValueChange = onAlamatChange,
            placeable = "Jl. Manggis",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Simpan",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}