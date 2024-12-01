package com.project.kasirku.presentation.Admin.Produk

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import android.net.Uri
import android.provider.MediaStore
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
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
import com.google.firebase.database.FirebaseDatabase
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
fun TambahProdukScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var idProduk by remember { mutableStateOf("") }
    var namaProduk by remember { mutableStateOf("") }
    var merk by remember { mutableStateOf("") }
    var stok by remember { mutableStateOf("") }
    var hargaBeli by remember { mutableStateOf("") }
    var hargaJual by remember { mutableStateOf("") }
    var satuan by remember { mutableStateOf("") }
    var selectedKategori by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val storageReference = FirebaseStorage.getInstance().reference
    val database = FirebaseDatabase.getInstance().getReference("produk")
    var isUploading by remember { mutableStateOf(false) }
    fun uploadImageToFirebaseStorage(fileUri: Uri, onSuccess: (String) -> Unit) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storageReference.child("produk_images/$fileName")

        isUploading = true

        ref.putFile(fileUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri -> //ngamgil link download url
                    onSuccess(uri.toString())
                    isUploading = false
                }
            }
            .addOnFailureListener {
                isUploading = false
                Toast.makeText(context, "Gagal upload foto", Toast.LENGTH_SHORT).show()
            }
    }

    fun addProdukToDatabase(downloadUrl: String) {
        val produkData = Produk(
            idProduk = idProduk,
            kategori = selectedKategori,
            namaProduk = namaProduk,
            merk = merk,
            stok = stok.toIntOrNull() ?: 0,
            hargaBeli = hargaBeli.toIntOrNull() ?: 0,
            hargaJual = hargaJual.toIntOrNull() ?: 0,
            satuan = satuan,
            produkImageUrl = downloadUrl
        )

        idProduk?.let {
            database.child(it).setValue(produkData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil menambahkan produk", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Produk.route) {
                        popUpTo(Screen.TambahProduk.route) { inclusive = true }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Gagal menambahkan produk", Toast.LENGTH_SHORT).show()
                }
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

    TambahProdukContent(
        idProduk = idProduk,
        namaProduk = namaProduk,
        merk = merk,
        stok = stok,
        hargaBeli = hargaBeli,
        hargaJual = hargaJual,
        satuan = satuan,
        selectedKategori = selectedKategori,
        imageUri = imageUri,
        isUploading = isUploading,
        onIdProdukChange = { idProduk = it },
        onNamaProdukChange = { namaProduk = it },
        onMerkChange = { merk = it },
        onStokChange = { stok = it },
        onHargaBeliChange = { hargaBeli = it },
        onHargaJualChange = { hargaJual = it },
        onSatuanChange = { satuan = it },
        onKategoriChange = { selectedKategori = it },
        onImageClick = { launcher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)) },
        onTambahProdukClick = {
            imageUri?.let { uri ->
                uploadImageToFirebaseStorage(uri) { downloadUrl ->
                    addProdukToDatabase(downloadUrl)
                }
            }
        },
        navController,
        modifier
    )

}

@Composable
fun TambahProdukContent(
    idProduk: String,
    namaProduk: String,
    merk: String,
    stok: String,
    hargaBeli: String,
    hargaJual: String,
    satuan: String,
    selectedKategori: String,
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
    onTambahProdukClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TitleItem(
                title = "Tambah Produk",
                onBackClick = {
                    navController.navigate(Screen.Produk.route) {
                        popUpTo(Screen.TambahProduk.route) { inclusive = true }
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
                DropdownKategoriTambahProdukItem(
                    title = "Kategori",
                    selectedKategori = selectedKategori,
                    onKategoriChange = onKategoriChange
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
                        painter = imageUri?.let {
                            rememberAsyncImagePainter(it)
                        } ?: painterResource(id = R.drawable.image_add_product),
                        contentDescription = "Produk Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (idProduk.isEmpty() || selectedKategori.isEmpty() || namaProduk.isEmpty() || merk.isEmpty() || stok.isEmpty() || hargaBeli.isEmpty() || hargaJual.isEmpty() || satuan.isEmpty()) {
                            Toast.makeText(context, "Data belum lengkap", Toast.LENGTH_SHORT).show()
                        } else if (imageUri == null) {
                            Toast.makeText(context, "Harap pilih gambar produk", Toast.LENGTH_SHORT).show()
                        } else {
                            onTambahProdukClick()
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
                        text = "Tambah",
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
