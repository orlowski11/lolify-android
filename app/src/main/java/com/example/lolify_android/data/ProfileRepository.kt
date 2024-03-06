package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(): Flow<Result<Profile>>
}