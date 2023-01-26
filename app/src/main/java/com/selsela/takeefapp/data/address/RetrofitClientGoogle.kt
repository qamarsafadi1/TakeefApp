package com.selsela.takeefapp.data.address
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.yariksoffice.lingver.Lingver
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClientGoogle {

    companion object {
        const val TIME_OUT = 60 * 1000L
        var BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/"
      //  var BASE_IMAGE_URL = "https://maintenance.selsela.site/uploads/"

        private val RETROFIT_INSTANCE: Retrofit by lazy {
            val okHttpBuilder = OkHttpClient.Builder()
            val loggingInterceptor = LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Platform.WARN)
                .request("|==Req==|  ")
                .response("|==Response==|  ")
                .build()
             okHttpBuilder.addInterceptor(loggingInterceptor)
                okHttpBuilder.addInterceptor { chain ->
                          val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("language", Lingver.getInstance().getLanguage())
                        .header("Accept","application/json")
                          /*  if(LocalData.user!=null)
                       requestBuilder.header("Authorization","Bearer ${LocalData.accessToken}")*/



                    val request = requestBuilder.build()
                    chain.proceed(request)
                }

                okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)

                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpBuilder.build())
                  //  .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

        }

        fun getRetrofit() =
            RETROFIT_INSTANCE

        fun <T> getService(clazz: Class<T>) = RETROFIT_INSTANCE.create(clazz)
    }
}