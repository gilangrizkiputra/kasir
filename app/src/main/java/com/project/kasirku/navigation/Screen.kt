package com.project.kasirku.navigation

sealed class Screen (val route: String) {
    data object Splash: Screen("splash")
    data object Masuk: Screen("masuk")
    data object Daftar: Screen("daftar")
    data object Beranda: Screen("beranda")
    data object Profil: Screen("profil")
    data object EditProfil: Screen("editProfil")
    data object GantiKataSandi: Screen("GantiKataSandi")
    data object Produk: Screen("produk")
    data object TambahProduk: Screen("tambahproduk")
    data object DetailProduk: Screen("detailproduk")
    data object EditProduk: Screen("editproduk")
    data object KategoriProduk: Screen("kategoriproduk")
    data object Riwayat: Screen("riwayat")
    data object DetailTransaksi: Screen("detailtransaksi")
    data object Pengaturan: Screen("pengaturan")
    data object BerandaKasir: Screen("berandaKasir")
    data object Keranjang: Screen("keranjang")
}