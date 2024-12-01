package com.project.kasirku.presentation.Admin.Produk

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.project.kasirku.R
import com.project.kasirku.model.Produk
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Produk.component.DropdownKategoriTambahProdukItem
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary
import java.util.UUID

@Composable
fun EditProdukScreen(
    produkId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var idProduk by remember { mutableStateOf(produkId) }
    var namaProduk by remember { mutableStateOf("") }
    var merk by remember { mutableStateOf("") }
    var stok by remember { mutableStateOf("") }
    var hargaBeli by remember { mutableStateOf("") }
    var hargaJual by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var produkImageUrl by remember { mutableStateOf("") }

    val database = FirebaseDatabase.getInstance().getReference("produk")
    var isUploading by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(idProduk) {
        database.child(idProduk).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val produk = snapshot.getValue(Produk::class.java)
                produk?.let {
                    namaProduk = it.namaProduk ?: ""
                    merk = it.merk ?: ""
                    stok = it.stok.toString()
                    hargaBeli = it.hargaBeli.toString()
                    hargaJual = it.hargaJual.toString()
                    satuan = it.satuan ?: ""
                    kategori = it.kategori ?: ""
                    produkImageUrl = it.produkImageUrl ?: ""
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Gagal memuat data produk", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun uploadImageToFirebaseStorage(fileUri: Uri, onSuccess: (String) -> Unit) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = FirebaseStorage.getInstance().reference.child("produk_images/$fileName")

        isUploading = true

        ref.putFile(fileUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                    isUploading = false
                }
            }
            .addOnFailureListener {
                isUploading = false
                Toast.makeText(context, "Gagal upload foto", Toast.LENGTH_SHORT).show()
            }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                imageUri = uri
            }
        }
    }

    fun updateProdukInDatabase(imageUrl: String) {
        val produk = Produk(
            idProduk = idProduk,
            namaProduk = namaProduk,
            merk = merk,
            stok = stok.toInt(),
            hargaBeli = hargaBeli.toInt(),
            hargaJual = hargaJual.toInt(),
            satuan = satuan,
            kategori = kategori,
            produkImageUrl = imageUrl
        )

        database.child(idProduk).setValue(produk)
            .addOnSuccessListener {
                Toast.makeText(context, "Produk berhasil diperbarui", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Produk.route){
                    popUpTo(Screen.EditProduk.route) { inclusive = true }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Gagal memperbarui produk", Toast.LENGTH_SHORT).show()
            }
    }

    fun onSimpanProdukClick() {
        if (imageUri != null) {
            uploadImageToFirebaseStorage(imageUri!!) { downloadUrl ->
                updateProdukInDatabase(downloadUrl)
            }
        } else {
            updateProdukInDatabase(produkImageUrl)
        }
    }

    EditProdukContent(
        idProduk = idProduk,
        namaProduk = namaProduk,
        merk = merk,
        stok = stok,
        hargaBeli = hargaBeli,
        hargaJual = hargaJual,
        satuan = satuan,
        kategori = kategori,
        produkImageUrl = produkImageUrl,
        imageUri = imageUri,
        isUploading = isUploading,
        onIdProdukChange = { idProduk = it },
        onNamaProdukChange = { namaProduk = it },
        onMerkChange = { merk = it },
        onStokChange = { stok = it },
        onHargaBeliChange = { hargaBeli = it },
        onHargaJualChange = { hargaJual = it },
        onSatuanChange = { satuan = it },
        onKategoriChange = { kategori = it },
        onImageClick = { launcher.launch(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)) },
        onSimpanProdukClick = { onSimpanProdukClick() },
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun EditProdukContent(
    idProduk: String,
    namaProduk: String,
    merk: String,
    stok: String,
    hargaBeli: String,
    hargaJual: String,
    satuan: String,
    kategori: String,
    produkImageUrl: String,
    imageUri: Uri?,
    isUploading: Boolean,
    onIdProdukChange: (String) -> Unit,
    onNamaProdukChange: (String) -> Unit,
    onMerkChange: (String) -> Unit,
    onStokChange: (String) -> Unit,
    onHargaBeliChange: (String) -> Unit,
    onHargaJualChange: (String) -> Unit,
    onSatuanChange: (String) -> Unit,
    onKategoriChange: (String) -> Unit,
    onImageClick: () -> Unit,
    onSimpanProdukClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            TitleItem(
                title = "Edit Produk",
                onBackClick = {
                    navController.navigate(Screen.Produk.route){
                        popUpTo(Screen.EditProduk.route) { inclusive = true }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = modifier.verticalScroll(scrollState)
            ) {
                FormItem(
                    title = "Id Produk",
                    value = idProduk,
                    onValueChange = onIdProdukChange,
                    placeable = "Masukkan ID produk",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Kategori",
                    value = kategori,
                    onValueChange = onKategoriChange,
                    placeable = "Masukkan nama kategori",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Nama produk",
                    value = namaProduk,
                    onValueChange = onNamaProdukChange,
                    placeable = "Masukkan nama produk",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Merk",
                    value = merk,
                    onValueChange = onMerkChange,
                    placeable = "Masukkan merk",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Stok",
                    value = stok,
                    onValueChange = onStokChange,
                    placeable = "Masukkan stok",
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Harga Beli",
                    value = hargaBeli,
                    onValueChange = onHargaBeliChange,
                    placeable = "Masukkan harga beli",
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Harga Jual",
                    value = hargaJual,
                    onValueChange = onHargaJualChange,
                    placeable = "Masukkan harga jual",
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormItem(
                    title = "Satuan",
                    value = satuan,
                    onValueChange = onSatuanChange,
                    placeable = "Masukkan satuan",
                    keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Foto Produk",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onImageClick() }
                ) {
                    Image(
                        painter =imageUri?.let {
                            rememberAsyncImagePainter(it)
                        } ?: if (produkImageUrl.isNotEmpty()) {
                            rememberAsyncImagePainter(produkImageUrl)
                        } else {
                            painterResource(id = R.drawable.image_add_product)
                        },
                        contentDescription = "Produk Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (idProduk.isEmpty() || kategori.isEmpty() || namaProduk.isEmpty() || merk.isEmpty() || stok.isEmpty() || hargaBeli.isEmpty() || hargaJual.isEmpty() || satuan.isEmpty()){
                            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }else{
                            onSimpanProdukClick()
                        }
                    },
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

        if (isUploading) {
            Box(
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    .background(Color.White)
                    .align(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    color = primary,
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 4.dp
                )
            }
        }
    }
}