package com.selsela.takeefapp.data.auth.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.auth.model.auth.AuthResponse
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.auth.model.notifications.NotificationResponse
import com.selsela.takeefapp.data.auth.model.support.ContactReplies
import com.selsela.takeefapp.data.auth.model.support.SupportResponse
import com.selsela.takeefapp.data.auth.model.support.contacts.Contact
import com.selsela.takeefapp.data.auth.model.wallet.WalletResponse
import com.selsela.takeefapp.data.auth.source.remote.AuthApi
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Constants.NOT_VERIFIED
import com.selsela.takeefapp.utils.Extensions.Companion.handleExceptions
import com.selsela.takeefapp.utils.Extensions.Companion.handleSuccess
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
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

    suspend fun verifyCode(
        code: String
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            body["mobile"] = LocalData.user?.mobile ?: ""
            body["code"] = code
            body["country_id"] = "1"
            val response = api.verifyCode(body)
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
                errorBase.log("errorBase")
                handleExceptions(errorBase)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun me(): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val response = api.me()
            if (response.isSuccessful) {
                LocalData.accessToken = response.body()?.user?.accessToken ?: ""
                LocalData.user = response.body()?.user
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                val gson = Gson()
                val errorBase = gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                errorBase.log("errorBase")
                handleExceptions(errorBase)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun getWallet(): Flow<Resource<WalletResponse>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<WalletResponse>> = try {
            val response = api.getWallet()
            if (response.isSuccessful) {
                LocalData.userWallet = response.body()
                handleSuccess(
                    response.body(),
                    response.body()?.responseMessage ?: response.message()
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

    suspend fun updateFCM(
    ): Flow<Resource<Boolean>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<Boolean>> = try {
                val response =
                    if (LocalData.accessToken.isNullOrEmpty() || LocalData.user?.status == NOT_VERIFIED)
                        api.updateFCM()
                    else api.updateUserFCM()
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body()?.status,
                        message = response.body()?.responseMessage ?: response.message() ?: ""
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

    suspend fun updateProfile(
        avatar: File?,
        name: String,
        mobile: String,
        email: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, String>()
            body["name"] = name
            body["mobile"] = mobile
            body["country_id"] = "1"
            body["email"] = email
            val map: HashMap<String, RequestBody> = hashMapOf()
            body.forEach { entry ->
                " ${entry.key} = ${entry.value}"
                run { map[entry.key] = Common.createPartFromString(entry.value) }
            }
            val userImage: MultipartBody.Part? = if (avatar != null)
                Common.createImageFile(avatar, "avatar") else null
            val response = api.updateProfile(userImage, map)
            if (response.isSuccessful) {
                LocalData.user = response.body()?.user
                LocalData.accessToken = response.body()?.user?.accessToken ?: ""
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() != 451) {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                } else {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    LocalData.user = responseUser?.user
                    handleSuccess(
                        responseUser?.user, responseUser?.responseMessage ?: response.message()
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun getNotification(): Flow<Resource<NotificationResponse>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<NotificationResponse>> = try {
            val response = api.getNotifications()
            if (response.isSuccessful) {
                 handleSuccess(
                    response.body(),
                    response.body()?.responseMessage ?: response.message()
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
    suspend fun deleteNotification(
        id: Int
    ): Flow<Resource<NotificationResponse>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<NotificationResponse>> = try {
            val response = api.deleteNotification(id)
            if (response.isSuccessful) {
                 handleSuccess(
                    response.body(),
                    response.body()?.responseMessage ?: response.message()
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

    suspend fun getContacts(
    ): Flow<Resource<List<ContactReplies>>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<List<ContactReplies>>> = try {
                val response = api.getContacts()
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body()?.contacts,
                        message = response.body()?.responseMessage ?: response.message() ?: ""
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

    suspend fun contactAdmin(
        text: String
    ): Flow<Resource<SupportResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<SupportResponse>> = try {
                val body = HashMap<String, Any>()
                body["name"] = LocalData.user?.name ?: ""
                body["mobile"] = LocalData.user?.mobile ?: ""
                body["type"] = Constants.CONTACT
                body["message"] = text
                body["country_id"] = "1"
                val response = api.contactAdmin(body)
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body(),
                        message = response.body()?.responseMessage ?: response.message() ?: ""
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

    suspend fun replySupport(
        text: String,
        contact_id: Int,
    ): Flow<Resource<SupportResponse>> =
        withContext(Dispatchers.IO) {
            val data: Flow<Resource<SupportResponse>> = try {
                val body = HashMap<String, Any>()
                body["name"] = LocalData.user?.name ?: ""
                body["mobile"] = LocalData.user?.mobile ?: ""
                body["type"] = Constants.REPLY
                body["message"] = text
                body["country_id"] = "1"
                body["contact_id"] = contact_id
                val response = api.contactAdmin(body)
                if (response.isSuccessful) {
                    handleSuccess(
                        response.body(),
                        message = response.body()?.responseMessage ?: response.message() ?: ""
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