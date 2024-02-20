package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import kotlinx.coroutines.flow.Flow


interface ChampionsRepository {
    suspend fun getChampionList(): Flow<Result<List<Champion>>>
}