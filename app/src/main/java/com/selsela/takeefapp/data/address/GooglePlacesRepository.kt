package com.selsela.takeefapp.data.address

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.selsela.takeefapp.data.auth.model.address.AddressResponse
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GooglePlacesRepository @Inject constructor(
) {
    private var service = RetrofitClientGoogle.getService(GooglePlacesApi::class.java)

    companion object {
        private var INSTANCE: GooglePlacesRepository? = null
        fun getInstance() =
            INSTANCE ?: GooglePlacesRepository().also { INSTANCE = it }
    }

    suspend fun getGoogleAddress(query: String): Flow<Resource<GooglePredictionsResponse>> =
        withContext(Dispatchers.IO) {
        val data: Flow<Resource<GooglePredictionsResponse>> = try {
            val response = service.getPredictions(
                input = query
            )
            if (response.isSuccessful) {
                Extensions.handleSuccess(
                    response.body(),
                    response.message()
                )
            } else {
                val gson = Gson()
                val errorBase = gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                Extensions.Companion.handleExceptions(errorBase)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Extensions.handleExceptions(e)
        }
        data
    }

}