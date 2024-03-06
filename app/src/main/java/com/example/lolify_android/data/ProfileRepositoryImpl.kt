package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import com.example.lolify_android.data.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val api: ApiInterface,
    private val username: String,
    private val token: String
): ProfileRepository {
    override suspend fun getUserProfile(): Flow<Result<Profile>> {
        return flow{
            val profile = try{
                api.getUserProfile(username, "Bearer $token")
            } catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading profile"))
                return@flow
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading profile"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading profile"))
                return@flow
            }

            emit(Result.Success(profile))
        }
    }
}