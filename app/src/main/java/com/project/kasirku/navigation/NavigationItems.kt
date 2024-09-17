package com.project.kasirku.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItems(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val badgeCount: Int? = null
)