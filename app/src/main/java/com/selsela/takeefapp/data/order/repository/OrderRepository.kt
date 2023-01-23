package com.selsela.takeefapp.data.order.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.order.model.order.OrderResponse
import com.selsela.takeefapp.data.order.model.special.SpecialOrderResponse
import com.selsela.takeefapp.data.order.remote.OrderApi
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: OrderApi
) {
    suspend fun getOrders(page: Int): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val response = api.getOrder(page)
                if (response.isSuccessful) {
                    Extensions.handleSuccess(
                        response.body(),
                        response.body()?.responseMessage ?: response.message()
                    )
                } else {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    Extensions.handleExceptions(errorBase)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Extensions.handleExceptions(e)
            }
            data
        }

}
