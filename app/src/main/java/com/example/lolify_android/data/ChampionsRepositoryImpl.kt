package com.example.lolify_android.data

import androidx.compose.ui.platform.LocalContext
import com.example.lolify_android.RetrofitInstance
import com.example.lolify_android.data.model.Champion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class ChampionsRepositoryImpl(
    private val api: ApiInterface
): ChampionsRepository {
    override suspend fun getChampionList(): Flow<Result<List<Champion>>> {
        return flow{
            val championsFromApi = try{
                api.getChampionList()
            } catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading champions"))
                return@flow
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading champions"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading champions"))
                return@flow
            }

            emit(Result.Success(championsFromApi))
        }
    }
}