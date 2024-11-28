package com.project.kasirku.presentation.Kasir.Keranjang

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.kasirku.model.CartItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random
fun saveOrderToFirebase(
    namaPelanggan: String,
    catatan: String,
    totalHarga: Int,
    bayar: Int,
    kembali: Int,
    keuntungan: Int,
    cartItems: MutableList<CartItem>,
    onSuccess: (String) -> Unit,  // Mengembalikan orderId pada callback success
    onFailure: (String) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Periksa apakah pengguna sedang login
    if (currentUser != null) {
        val userId = currentUser.uid

        // Referensi ke database pengguna berdasarkan userId
        val userDatabase = FirebaseDatabase.getInstance().getReference("user").child(userId)

        // Ambil data pengguna dari Firebase Realtime Database
        userDatabase.get().addOnSuccessListener { dataSnapshot ->
            val username = dataSnapshot.child("username").getValue(String::class.java)

            // Jika username berhasil diambil
            if (username != null) {
                // Buat pesanan dan masukkan username kasir
                val database = FirebaseDatabase.getInstance().getReference("orders")
                val produkDatabase = FirebaseDatabase.getInstance().getReference("produk")

                // Generate random orderId
                val orderId = Random.nextInt(1000, 9999).toString()

                val order = mutableMapOf<String, Any>(
                    "orderId" to orderId,
                    "kasir" to username, // Masukkan username kasir
                    "namaPelanggan" to namaPelanggan,
                    "catatan" to catatan,
                    "totalHarga" to totalHarga,
                    "bayar" to bayar,
                    "kembali" to kembali,
                    "keuntungan" to keuntungan,
                    "tanggal" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy, HH:mm", Locale("id", "ID"))),
                    "items" to cartItems.map { item ->
                        mapOf(
                            "idProduk" to item.produk.idProduk,
                            "namaProduk" to item.produk.namaProduk,
                            "hargaJual" to item.produk.hargaJual,
                            "quantity" to item.quantity
                        )
                    }
                )

                // Simpan pesanan ke database
                database.child(orderId).setValue(order)
                    .addOnSuccessListener {
                        // Loop melalui cartItems dan kurangi stok produk di Firebase
                        cartItems.forEach { item ->
                            val produkRef = item.produk.idProduk?.let { it1 -> produkDatabase.child(it1) }
                            produkRef?.get()?.addOnSuccessListener { dataSnapshot ->
                                val stokSaatIni = dataSnapshot.child("stok").getValue(Int::class.java) ?: 0
                                val stokBaru = stokSaatIni - item.quantity

                                // Update stok produk di Firebase
                                if (stokBaru >= 0) {
                                    produkRef?.child("stok")?.setValue(stokBaru)
                                } else {
                                    onFailure("Stok tidak mencukupi untuk produk ${item.produk.namaProduk}")
                                }
                            }?.addOnFailureListener { exception ->
                                onFailure("Gagal memperbarui stok produk ${item.produk.namaProduk}: ${exception.message}")
                            }
                        }

                        // Panggil onSuccess dan kirimkan orderId
                        onSuccess(orderId)
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception.message ?: "Unknown error")
                    }
            } else {
                onFailure("Gagal menemukan username pengguna di database.")
            }
        }.addOnFailureListener { exception ->
            onFailure("Gagal mengambil data pengguna: ${exception.message}")
        }
    } else {
        onFailure("Tidak ada pengguna yang sedang login.")
    }
}

