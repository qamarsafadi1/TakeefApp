package com.selsela.takeefapp.data.order.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.order.OrderResponse
import com.selsela.takeefapp.data.order.model.special.SpecialOrderResponse
import com.selsela.takeefapp.data.order.model.special.SpecificOrder
import com.selsela.takeefapp.data.order.remote.OrderApi
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: OrderApi
) {
    suspend fun getOrders(page: Int, caseID: Int): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val response = api.getOrder(page, caseID)
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

    suspend fun getOrderDetails(orderId: Int): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val response = api.getOrderDetails(orderId)
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

    suspend fun placeOrder(
        services: String,
        orderDate: String,
        workPeriodId: Int,
        useWallet: Int = 0,
        paymentTypeId: Int? = null,
        areaId: Int,
        cityId: Int,
        districtId: Int,
        lat: Double,
        lng: Double,
        note: String? = "",
        isFav: Int = 0
    ): Flow<Resource<Order>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<Order>> = try {
            val body = HashMap<String, String>()
            body["services"] = services
            body["order_date"] = orderDate
            body["work_period_id"] = "$workPeriodId"
            body["use_wallet"] = "$useWallet"
            if (paymentTypeId != null && paymentTypeId != -1)
                body["payment_type_id"] = "$paymentTypeId"
            body["lng"] = "$lng"
            body["lat"] = "$lat"
            body["note"] = "$note"
            body["district_id"] = "$districtId"
            body["city_id"] = "$cityId"
            body["area_id"] = "$areaId"
            body["is_fav"] = "$isFav"

            val response = api.placeOrder(body)
            if (response.isSuccessful) {
                Extensions.handleSuccess(
                    response.body()?.order,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                val gson = Gson()
                val errorBase =
                    gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                Extensions.Companion.handleExceptions(errorBase)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Extensions.handleExceptions(e)
        }
        data
    }

}
