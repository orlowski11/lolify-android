package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import kotlinx.coroutines.flow.Flow

interface Top3ChampionsRepository {
    suspend fun getTop3Champions(): Flow<Result<List<Champion>>>
}