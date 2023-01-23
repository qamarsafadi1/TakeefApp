package com.selsela.takeefapp.data.order.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.auth.model.notifications.NotificationResponse
import com.selsela.takeefapp.data.auth.source.remote.AuthApi
import com.selsela.takeefapp.data.order.model.special.SpecialOrderResponse
import com.selsela.takeefapp.data.order.model.special.SpecificOrder
import com.selsela.takeefapp.data.order.remote.SpecialOrderApi
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.handleExceptions
import com.selsela.takeefapp.utils.Extensions.Companion.handleSuccess
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class SpecialOrderRepository @Inject constructor(
    private val api: SpecialOrderApi
) {
    suspend fun placeSpecialOrder(
        username: String,
        title: String,
        description: String,
        photos: List<File>?,
    ): Flow<Resource<SpecificOrder>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<SpecificOrder>> = try {
            val body = HashMap<String, String>()
            body["user_name"] = "$username"
            body["title"] = "$title"
            body["description"] = description
            val map: HashMap<String, RequestBody> = hashMapOf()
            body.forEach { entry ->
                " ${entry.key} = ${entry.value}"
                run { map[entry.key] = Common.createPartFromString(entry.value) }
            }
            val parts = ArrayList<MultipartBody.Part>()
            if (photos?.isEmpty() == false) {
                for (i in photos.indices) {
                    parts.add(Common.createImageFile(photos[i], "images[$i]"))
                }
            } else {
                body["images[]"] = ""
            }
            val response = api.placeSpecialOrder(parts, map)
            if (response.isSuccessful) {
                handleSuccess(
                    response.body()?.specificOrder,
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

    suspend fun getSpecialOrders(): Flow<Resource<SpecialOrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<SpecialOrderResponse>> = try {
                val response = api.getSpecialOrders()
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body(),
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

    suspend fun getSpecialOrderDetails(id: Int): Flow<Resource<SpecialOrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<SpecialOrderResponse>> = try {
                val response = api.getSpecialOrderDetails(id)
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body(),
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


}
