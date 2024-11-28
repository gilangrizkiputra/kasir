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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.project.kasirku.R
import com.project.kasirku.data.DataStorage
import com.project.kasirku.data.fetchUserData
import com.project.kasirku.model.User
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.component.FormItem
import com.project.kasirku.presentation.component.TitleItem
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary
import java.util.UUID

@Composable
fun EditProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("user")

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

    val currentUser = auth.currentUser
    val uid = currentUser?.uid

    LaunchedEffect(Unit) {
        uid?.let {
            database.child(it).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()){
                    username = snapshot.child("username").value?.toString() ?: "-"
                    email = snapshot.child("email").value?.toString() ?: "-"
                    telephone = snapshot.child("telephone").value?.toString() ?: "-"
                    alamat = snapshot.child("alamat").value?.toString() ?: "-"
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        }
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
        onSimpanClick = {
            uid?.let {
                val userMap = mapOf(
                    "username" to if (username.isNotEmpty()) username else "-",
                    "email" to if (email.isNotEmpty()) email else "-",
                    "telephone" to if (telephone.isNotEmpty()) telephone else "-",
                    "alamat" to if (alamat.isNotEmpty()) alamat else "-"
                )
                database.child(it).updateChildren(userMap).addOnSuccessListener {
                    Toast.makeText(context, "Profil berhasil diupdate", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Profil.route){
                        popUpTo(Screen.EditProfil.route) { inclusive = true }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Gagal mengupdate profil", Toast.LENGTH_SHORT).show()
                }
            }
        },
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
    val context = LocalContext.current
    var user by remember { mutableStateOf<User?>(null) }
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().getReference("user")
    val storageReference = FirebaseStorage.getInstance().reference
    val userId = auth.currentUser?.uid


    // State untuk menyimpan URL gambar profil dan status loading
    var profileImageUrl by remember { mutableStateOf<String?>("-") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) } // Status untuk progress indicator


    // Fungsi untuk mengunggah gambar ke Firebase Storage
    fun uploadImageToFirebaseStorage(fileUri: Uri, onSuccess: (String) -> Unit) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val ref = storageReference.child("profile_images/$fileName")

        isUploading = true // Mulai progress indicator

        ref.putFile(fileUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString()) // Callback URL unduhan gambar
                    isUploading = false // Selesai, matikan progress indicator
                }
            }
            .addOnFailureListener {
                isUploading = false // Matikan progress indicator jika gagal
                Toast.makeText(context, "Gagal upload foto", Toast.LENGTH_SHORT).show()
            }
    }

    // Buka galeri & pilih gambar
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                imageUri = uri // Tampilkan gambar sementara dari URI lokal
                uploadImageToFirebaseStorage(uri) { downloadUrl ->
                    profileImageUrl = downloadUrl // Simpan URL gambar dari Firebase
                    // Simpan URL ke Firebase Realtime Database
                    userId?.let { uid ->
                        database.child(uid).child("profileImageUrl").setValue(downloadUrl)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Berhasil mengganti foto profil", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Gagal mengganti foto profil", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        }
    }

    LaunchedEffect(userId) {
        if (userId != null) {
            fetchUserData(userId) { fetchedUser ->
                user = fetchedUser
            }
        }
    }

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
        user?.let { userData ->
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(100.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = userData.profileImageUrl),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp)
                )

                if (isUploading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(40.dp),
                        color = primary,
                        strokeWidth = 4.dp
                    )
                }

                Surface(
                    color = primary,
                    shadowElevation = 0.dp,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x = (-1).dp, y = (-6).dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            launcher.launch(intent)
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit_produk),
                        contentDescription = "Edit Icon",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        FormItem(
            title = "Username",
            value = username,
            onValueChange = onUsernameChange,
            placeable = "Masukkan username",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Email",
            value = email,
            onValueChange = onEmailChange,
            placeable = "Masukkan email",
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Telephone",
            value = telephone,
            onValueChange = onTelephoneChange,
            placeable = "Masukkan no telephone",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormItem(
            title = "Alamat",
            value = alamat,
            onValueChange = onAlamatChange,
            placeable = "Masukkan alamat",
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onSimpanClick() },
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