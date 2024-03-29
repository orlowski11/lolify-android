package com.example.lolify_android.data

import com.example.lolify_android.data.model.Champion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class Top3ChampionsRepositoryImpl(
    private val api: ApiInterface
) : Top3ChampionsRepository {
    override suspend fun getTop3Champions(): Flow<Result<List<Champion>>> {
        return flow {
            val championsFromApi = try {
                api.getTop3Champions()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading champions"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading champions"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading champions"))
                return@flow
            }

            emit(Result.Success(championsFromApi))
        }
    }
}