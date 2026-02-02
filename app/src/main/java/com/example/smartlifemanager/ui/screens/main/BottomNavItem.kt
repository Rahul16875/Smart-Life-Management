package com.example.smartlifemanager.ui.screens.main

import com.example.smartlifemanager.R
import com.example.smartlifemanager.ui.navigation.Routes

data class BottomNavItem(
    val route: String,
    val title: String,
    val iconResId: Int
)

fun getBottomNavItems(): List<BottomNavItem> = listOf(
    BottomNavItem(Routes.HOME, "Home", R.drawable.house),
    BottomNavItem(Routes.TASKS, "Tasks", R.drawable.task),
    BottomNavItem(Routes.HABITS, "Habits", R.drawable.habits),
    BottomNavItem(Routes.EXPENSES, "Wallet", R.drawable.expenses),
    BottomNavItem(Routes.NOTES, "Notes", R.drawable.notes)
)
