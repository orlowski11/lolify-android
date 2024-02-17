package com.example.lolify_android

sealed class Screen(val route: String) {
    object Homepage : Screen("homepage")
    object ChampionList : Screen("champion_list")
}