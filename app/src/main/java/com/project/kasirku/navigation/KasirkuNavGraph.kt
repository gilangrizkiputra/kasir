package com.project.kasirku.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.kasirku.presentation.Admin.Beranda.BerandaScreen
import com.project.kasirku.presentation.Admin.Detail.DetailTransaksiScreen
import com.project.kasirku.presentation.Admin.Kategori.KategoriScreen
import com.project.kasirku.presentation.Admin.Pengaturan.PengaturanScreen
import com.project.kasirku.presentation.Admin.Produk.DetailProdukScreen
import com.project.kasirku.presentation.Admin.Produk.EditProdukScreen
import com.project.kasirku.presentation.Admin.Produk.ProdukScreen
import com.project.kasirku.presentation.Admin.Produk.TambahProdukScreen
import com.project.kasirku.presentation.Admin.Riwayat.RiwayatScreen
import com.project.kasirku.presentation.Kasir.Beranda.BerandaKasirScreen
import com.project.kasirku.presentation.Kasir.Keranjang.KeranjangScreen
import com.project.kasirku.presentation.Login.LoginScreen
import com.project.kasirku.presentation.Register.RegistrasiScreen
import com.project.kasirku.presentation.Splash.SplashScreen
import com.project.kasirku.presentation.profile.EditProfileScreen
import com.project.kasirku.presentation.profile.GantiKataSandiScreen
import com.project.kasirku.presentation.profile.ProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KasirkuNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Masuk.route) { LoginScreen(navController) }
        composable(Screen.Daftar.route) { RegistrasiScreen(navController) }
        composable(Screen.Beranda.route) {
            BerandaScreen(navController)
        }
        composable(Screen.Profil.route) { ProfileScreen(navController) }
        composable(Screen.EditProfil.route) { EditProfileScreen(navController) }
        composable(Screen.GantiKataSandi.route) { GantiKataSandiScreen(navController) }
        composable(Screen.Produk.route) { ProdukScreen(navController) }
        composable(Screen.TambahProduk.route) { TambahProdukScreen(navController) }
        composable(Screen.DetailProduk.route) { DetailProdukScreen(navController) }
        composable(Screen.EditProduk.route) { EditProdukScreen(navController) }
        composable(Screen.KategoriProduk.route) { KategoriScreen(navController) }
        composable(Screen.Riwayat.route) { RiwayatScreen(navController) }
        composable(Screen.DetailTransaksi.route) { DetailTransaksiScreen(navController) }
        composable(Screen.Pengaturan.route) { PengaturanScreen(navController) }
        composable(Screen.BerandaKasir.route) { BerandaKasirScreen(navController) }
        composable(Screen.Keranjang.route) { KeranjangScreen(navController) }
    }
}