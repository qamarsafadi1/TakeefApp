package com.selsela.takeefapp.data.order.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.order.OrderResponse
import com.selsela.takeefapp.data.order.remote.OrderApi
import com.selsela.takeefapp.utils.Constants.COD
import com.selsela.takeefapp.utils.Constants.WALLET
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

    suspend fun cancelOrder(orderId: Int): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val response = api.cancelOrder(orderId)
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

    suspend fun rateOrder(
        orderId: Int,
        rateList: MutableList<Any>,
        note: String?
    ): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val body = HashMap<String, Any>()
                body["order_id"] = orderId
                body["rate_properities"] = rateList
                if (note.isNullOrEmpty().not())
                    body["notes"] = note!!
                val response = api.rateOrder(body)
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
    suspend fun rejectAdditionalCost(
        orderId: Int
    ): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val body = HashMap<String, Any>()
                body["order_id"] = orderId
                body["additional_cost_status"] = "rejected"
                val response = api.changeAdditionalCostStatus(body)
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

    suspend fun acceptAdditionalCost(
        orderId: Int,
        paymentId: Int,
        useWallet: Int
    ): Flow<Resource<OrderResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<OrderResponse>> = try {
                val body = HashMap<String, Any>()
                body["order_id"] = orderId
                body["additional_cost_status"] = "accepted"
                body["use_wallet"] = useWallet
                if (paymentId != -1)
                body["payment_type_id"] = paymentId
                val response = api.changeAdditionalCostStatus(body)
                if (response.isSuccessful) {
                    if (paymentId == -1 || paymentId == WALLET || paymentId == COD) {
                        Extensions.handleSuccess(
                            response.body(),
                            response.body()?.responseMessage ?: response.message()
                        )
                    } else {
                        if (confirmPayment(
                                transactionId = response.body()?.transaction?.transactionId
                                    ?: "",
                                amount = response.body()?.transaction?.amount ?: 0.0,
                                paymentTypeId = response.body()?.transaction?.paymentType ?: 0,

                                )
                        ) {
                            Extensions.handleSuccess(
                                response.body(),
                                response.body()?.responseMessage ?: response.message()
                            )
                        } else {
                            Extensions.handleExceptions(
                                ErrorBase(
                                    null,
                                    null,
                                    "Something went wrong",
                                    status = false
                                )
                            )
                        }
                    }
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
                if (paymentTypeId == -1 || paymentTypeId == WALLET || paymentTypeId == COD) {
                    Extensions.handleSuccess(
                        response.body()?.order,
                        response.body()?.responseMessage ?: response.message()
                    )
                } else {
                    if (confirmPayment(
                            transactionId = response.body()?.order?.transaction?.transactionId
                                ?: "",
                            amount = response.body()?.order?.transaction?.amount ?: 0.0,
                            paymentTypeId = response.body()?.order?.transaction?.paymentType ?: 0,

                            )
                    ) {
                        Extensions.handleSuccess(
                            response.body()?.order,
                            response.body()?.responseMessage ?: response.message()
                        )
                    } else {
                        Extensions.handleExceptions(
                            ErrorBase(
                                null,
                                null,
                                "Something went wrong",
                                status = false
                            )
                        )
                    }
                }
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

    suspend fun confirmPayment(
        transactionId: String,
        amount: Double,
        paymentTypeId: Int
    ): Boolean = withContext(Dispatchers.IO) {
        val isConfirmed: Boolean = try {
            val body = HashMap<String, Any>()
            body["transaction_id"] = transactionId
            body["amount_paid"] = amount
            body["return_response"] = "$paymentTypeId"
            val response = api.confirmPayment(body)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            Extensions.handleExceptions<java.lang.Exception>(e)
            false
        }
        isConfirmed
    }

}
