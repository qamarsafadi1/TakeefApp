package com.selsela.takeefapp.data.config.repository

import com.google.gson.Gson
import com.selsela.takeefapp.data.config.model.Configurations
import com.selsela.takeefapp.data.config.model.page.Page
import com.selsela.takeefapp.data.config.source.remote.ConfigApi
import com.selsela.takeefapp.utils.Extensions.Companion.handleExceptions
import com.selsela.takeefapp.utils.Extensions.Companion.handleSuccess
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationsRepository @Inject constructor(
    private val api: ConfigApi
) {
    suspend fun getConfigurations(): Flow<Resource<Configurations>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<Configurations>> = try {
            val response = api.getConfigurations()
            if (response.isSuccessful) {
                withContext(Dispatchers.Default){
                    getTerms()
                    getAboutApp()
                }
                LocalData.configurations = response.body()?.configurations
                LocalData.cases = response.body()?.cases
                LocalData.acTypes = response.body()?.acTypes
                LocalData.services = response.body()?.services
              "${response.body()?.services}".log("Services")
                handleSuccess(
                    response.body()?.configurations,
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
    suspend fun getTerms(): Flow<Resource<Page>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<Page>> = try {
            val response = api.getTerms()
            if (response.isSuccessful) {
                LocalData.terms = response.body()?.page
                handleSuccess(
                    response.body()?.page,
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
    suspend fun getAboutApp(): Flow<Resource<Page>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<Page>> = try {
            val response = api.getAboutApp()
            if (response.isSuccessful) {
                LocalData.aboutApp = response.body()?.page
                handleSuccess(
                    response.body()?.page,
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
}