package com.project.kasirku.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.project.kasirku.R
import com.project.kasirku.data.DataStorage
import com.project.kasirku.data.fetchUserData
import com.project.kasirku.model.User
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.presentation.profile.component.ProfileItem
import com.project.kasirku.ui.theme.primary
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ProfileScreenContent(navController, modifier)
}

@Composable
fun ProfileScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataStorage = remember { DataStorage(context) }
    var user by remember { mutableStateOf<User?>(null) }
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    LaunchedEffect(userId) {
        if (userId != null) {
            fetchUserData(userId) { fetchedUser ->
                user = fetchedUser
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TitleItem(
            title = "Profile",
            onBackClick = {
                navController.popBackStack()
            }
        )
        Spacer(modifier = Modifier.height(34.dp))
        user?.let {userData ->
            Image(
                painter = rememberAsyncImagePainter(
                    model = userData.profileImageUrl,
//                        placeholder = painterResource(id = R.drawable.ic_akun_dashboard),
//                        error = painterResource(id = R.drawable.ic_akun_dashboard)
                ),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop

            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        ProfileItem(
            icon = R.drawable.ic_edit_profil,
            title = "Edit Profil",
            onItemClick = {
                navController.navigate(Screen.EditProfil.route){
                    popUpTo(Screen.Profil.route) { inclusive = true }
                }
            }
        )
        ProfileItem(
            icon = R.drawable.ic_ganti_kata_sandi,
            title = "Ganti Kata Sandi",
            onItemClick = {
                navController.navigate(Screen.GantiKataSandi.route){
                    popUpTo(Screen.Profil.route) { inclusive = true }
                }
            }
        )
        ProfileItem(
            icon = R.drawable.ic_keluar,
            title = "Keluar",
            onItemClick = {
                coroutineScope.launch {
                    dataStorage.clearLoginStatus()
                    navController.navigate(Screen.Masuk.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}


