package com.selsela.takeefapp.data.auth.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.auth.source.remote.AuthApi
import com.selsela.takeefapp.utils.Constants.NOT_VERIFIED
import com.selsela.takeefapp.utils.Extensions.Companion.handleExceptions
import com.selsela.takeefapp.utils.Extensions.Companion.handleSuccess
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi
) {
    suspend fun auth(
        mobile: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            body["mobile"] = mobile
            body["country_id"] = "1"
            val response = api.auth(body)
            if (response.isSuccessful) {
                LocalData.accessToken = response.body()?.user?.accessToken ?: ""
                LocalData.user = response.body()?.user
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                val gson = Gson()
                val errorBase =
                    gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                handleExceptions(errorBase)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun updateFCM(
    ): Flow<Resource<Boolean>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<Boolean>> = try {
                val response = if (LocalData.accessToken.isEmpty() || LocalData.user?.status == NOT_VERIFIED)
                    api.updateFCM()
                else api.updateUserFCM()
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body()?.status,
                        message = response.body()?.responseMessage ?: response.message() ?: ""
                    )
                } else {
                    val gson = Gson()
                    val errorBase = gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                handleExceptions(e)
            }
            data
        }
}