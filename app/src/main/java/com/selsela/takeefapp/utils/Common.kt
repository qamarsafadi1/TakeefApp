package com.selsela.takeefapp.utils

import android.content.Context
import android.graphics.Bitmap
import com.selsela.takeefapp.R
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.showError
import com.selsela.takeefapp.utils.retrofit.model.Errors
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File

open class Common {
    companion object{
        fun validImageSize(bitmapPhoto: Bitmap): Boolean {
            return try {
                val stream = ByteArrayOutputStream()
                bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageInByte: ByteArray = stream.toByteArray()


                // Get length of file in bytes
                val imageSizeInBytes = imageInByte.size.toFloat()
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                val imageSizeInKB = imageSizeInBytes / 1024
                // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                val imageSizeInMB = imageSizeInKB / 1024
                imageSizeInMB.log("imageSizeInMB")
                imageSizeInMB <= 20

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                true
            }
        }

        fun handleErrors(
            message: String?, errors: List<Errors>?, context: Context
        ) {
            when {
                errors != null -> {
                    if (errors.isNotEmpty())
                        context.showError(errors[0].error)
                    else context.showError(message!!)
                }
                message != null -> context.showError(message)
                else -> context.showError(context.getString(R.string._msg_failed))
            }
        }
        fun createPartFromString(descriptionString: String?): RequestBody {
            return descriptionString?.toRequestBody(MultipartBody.FORM) ?: "".toRequestBody(
                MultipartBody.FORM
            )
        }
        fun createImageFile(image: File, key: String): MultipartBody.Part {
            val requestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(key, image.name ?: "123", requestBody!!)
        }

    }
}