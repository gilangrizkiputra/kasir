package com.project.kasirku.presentation.profile

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.kasirku.R
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.presentation.profile.component.ProfileItem

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
        Image(
            painter = painterResource(id = R.drawable.ic_profil),
            contentDescription = "profile",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
        )
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
                navController.navigate(Screen.Masuk.route){
                    popUpTo(Screen.Profil.route) { inclusive = true }
                }
            }
        )
    }
}