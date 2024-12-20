package com.project.kasirku

import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.kasirku.data.DataStorage
import com.project.kasirku.data.fetchUserData
import com.project.kasirku.model.CartItem
import com.project.kasirku.model.User
import com.project.kasirku.navigation.KasirkuNavGraph
import com.project.kasirku.navigation.NavigationItems
import com.project.kasirku.navigation.Screen
import com.project.kasirku.presentation.Admin.Riwayat.ExportDataToExcel
import com.project.kasirku.ui.theme.poppinsFontFamily
import com.project.kasirku.ui.theme.primary
import com.project.kasirku.ui.theme.secondary
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KasirkuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val dataStorage = remember { DataStorage(context) }

    // State untuk menyimpan cart items
    val cartItems = remember { mutableStateListOf<CartItem>() }

    // Hitung total produk di keranjang
    val totalProduk = cartItems.sumOf { it.quantity }

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val title = remember { mutableStateOf("Beranda") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route
    val noTopAppBarScreens = listOf(
        Screen.Splash.route,
        Screen.Masuk.route,
        Screen.Profil.route,
        Screen.EditProfil.route,
        Screen.GantiKataSandi.route,
        Screen.TambahProduk.route,
        Screen.DetailProduk.route,
        Screen.EditProduk.route,
        Screen.DetailTransaksi.route,
        Screen.Keranjang.route
    )
    val shouldShowTopAppBar = noTopAppBarScreens.none { currentDestination?.startsWith(it) == true }

    val allowedGesturesDestinations = listOf(
        Screen.Beranda.route,
        Screen.Produk.route,
        Screen.KategoriProduk.route,
        Screen.Riwayat.route,
        Screen.Pengaturan.route
    )

    val navigationItems = listOf(
        NavigationItems(
            title = "Beranda",
            selectedIcon = painterResource(id = R.drawable.ic_beranda),
            unselectedIcon = painterResource(id = R.drawable.ic_beranda)
        ),
        NavigationItems(
            title = "Produk",
            selectedIcon = painterResource(id = R.drawable.ic_produk),
            unselectedIcon = painterResource(id = R.drawable.ic_produk)
        ),
        NavigationItems(
            title = "Kategori",
            selectedIcon = painterResource(id = R.drawable.ic_kategori),
            unselectedIcon = painterResource(id = R.drawable.ic_kategori)
        ),
        NavigationItems(
            title = "Riwayat Transaksi",
            selectedIcon = painterResource(id = R.drawable.ic_riwayat_transaksi),
            unselectedIcon = painterResource(id = R.drawable.ic_riwayat_transaksi)
        ),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentDestination in allowedGesturesDestinations,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = primary
            ) {
                user?.let { userData ->
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(Screen.Profil.route)
                                    scope.launch { drawerState.close() }

                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = userData.profileImageUrl,
//                                    placeholder = painterResource(id = R.drawable.ic_akun_dashboard),
//                                    error = painterResource(id = R.drawable.ic_akun_dashboard)
                                ),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = userData.username,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = userData.email,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    navigationItems.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.title, color = Color.White) },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                scope.launch { drawerState.close() }
                                when (index) {
                                    0 -> navController.navigate(Screen.Beranda.route){
                                        title.value = "Beranda"
                                    }
                                    1 -> navController.navigate(Screen.Produk.route){
                                        title.value = "Produk"
                                    }
                                    2 -> navController.navigate(Screen.KategoriProduk.route){
                                        title.value = "Kategori Produk"
                                    }
                                    3 -> navController.navigate(Screen.Riwayat.route){
                                        title.value = "Riwayat Transaksi"
                                    }
                                    else -> return@NavigationDrawerItem
                                }
                            },
                            icon = {
                                Icon(
                                    painter = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title,
                                    tint = Color.White
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = secondary,
                                unselectedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (shouldShowTopAppBar) {
                    Surface(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 10.dp,
                                    bottomEnd = 10.dp
                                )
                            ),
                        color = primary,
                    ) {
                        if (currentDestination == Screen.BerandaKasir.route){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_keluar),
                                    contentDescription = "cart",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clickable {
                                            coroutineScope.launch {
                                                dataStorage.clearLoginStatus()
                                                navController.navigate(Screen.Masuk.route) {
                                                    popUpTo(0) { inclusive = true }
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                )
                                Spacer(modifier = Modifier.padding(start = 16.dp))
                                Text(
                                    text = "Hi, Kasir",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Box(
                                        contentAlignment = Alignment.TopStart,
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .size(28.dp)
                                            .clickable {
                                                navController.navigate(Screen.Keranjang.route)
                                            } ,
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_cart),
                                            contentDescription = "cart",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .size(28.dp)
                                        )
                                        if (totalProduk > 0) {
                                            Surface(
                                                color = secondary,
                                                shadowElevation = 0.dp,
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .offset(x = (-8).dp, y = (-8).dp)
                                                    .clip(CircleShape)
                                            ) {
                                                Row (
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxSize()
                                                ){
                                                    Text(
                                                        text = totalProduk.toString(),
                                                        color = Color.White,
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(top = 1.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else{
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_menu),
                                        contentDescription = "Menu",
                                        tint = Color.White,
                                        modifier = Modifier.size(width = 36.dp, height = 20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = title.value,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            },
            content = { contentPadding ->
                KasirkuNavGraph(
                    navController = navController,
                    cartItems = cartItems,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        )
    }
}


