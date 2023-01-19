package com.selsela.takeefapp.utils
import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


open class FileHelper {
    companion object {
        var absoulutePath: String? = null

//        fun convertTotMultiPart(image: File?, key: String): MultipartBody.Part? {
//            val requestBody = image?.asRequestBody("image/*".toMediaTypeOrNull())
//            return requestBody?.let {
//                MultipartBody.Part.createFormData(
//                    key, image?.name ?: "123",
//                    it
//                )
//            }
//
//        }

        fun getFileName(context: Context, it: Uri): String {
            val uri = it
            val filename: String
            val cursor: Cursor? =
                context?.contentResolver?.query(uri, null, null, null, null)
            if (cursor == null) filename = uri.path ?: "" else {
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                val idx: Int =
                    cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                filename = cursor.getString(idx)
                cursor.close()
            }
            return filename
        }

        fun getImage(uri: Uri?, context: Context): File? {
            val bitmapImage = compressImage(context, uri)
            val cw = ContextWrapper(context)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val imageFileName = "JPEG_${Calendar.getInstance().timeInMillis}${Random(100)}.jpg"
            val mypath = File(directory, imageFileName)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return mypath
        }

        fun getBitmapImage(bitmap: Bitmap, context: Context): File? {
            val file =
                File(context.getExternalFilesDir(null)!!.path + File.separator +Calendar.getInstance().timeInMillis+""+Random(100)+ "temporary_file.jpg")
            val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.close()

            return file
        }

        fun compressImage(context: Context, selectedImage: Uri?): Bitmap? {
            var bm: Bitmap? = null
            /*   val sampleSizes = intArrayOf(5, 3, 2, 1)
            var i = 0
            do {
                bm = decodeBitmap(context, selectedImage, sampleSizes[i])
                Log.d("TAG", "resizer: new bitmap width = " + bm!!.width)
                i++
            } while (bm!!.width < 400 && i < sampleSizes.size)*/
            bm = selectedImage?.let { decodeBitmap(context, it) }

            return bm
        }

        fun decodeBitmap(
            context: Context, theUri: Uri?, sampleSize: Int
        ): Bitmap? {
            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize
            var fileDescriptor: AssetFileDescriptor? = null
            try {
                fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri!!, "r")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                fileDescriptor!!.fileDescriptor, null, options
            )
            Log.d(
                "TAG",
                options.inSampleSize.toString() + " sample method bitmap ... " + actuallyUsableBitmap.width + " " + actuallyUsableBitmap.height
            )
            return actuallyUsableBitmap
        }

        fun decodeBitmap(context: Context, imagePath: Uri): Bitmap? {


            var inputstream: InputStream?
            val exif: ExifInterface?
            var image: Bitmap? = null
            try {
                inputstream = context.contentResolver.openInputStream(imagePath)
                image = BitmapFactory.decodeStream(inputstream)

//Close input stream consumed for Bitmap decode
                inputstream?.close()

// Open stream again for reading exif information for acquiring orientation details.
                // Use new input stream otherwise bitmap decode stream gets reset.
                inputstream = context.contentResolver.openInputStream(imagePath)
                var orientation: Int = ExifInterface.ORIENTATION_UNDEFINED
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        exif = inputstream?.let { ExifInterface(it) }
                    } else {
                        exif = imagePath.getPath()?.let { ExifInterface(it) }
                    }
                    orientation = exif?.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                    ) ?: ExifInterface.ORIENTATION_NORMAL
                } catch (e: IOException) {
// Log.d("IOException: " + e.message)
                }

//if you need, can correct orientation issues for gallery pick camera images with following.
                //Log.d("decodeBitmap orientation: $orientation")
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90, ExifInterface.ORIENTATION_TRANSPOSE -> image =
                        TransformationUtils.rotateImage(image, 90)
                    ExifInterface.ORIENTATION_ROTATE_180, ExifInterface.ORIENTATION_FLIP_VERTICAL -> image =
                        TransformationUtils.rotateImage(image, 180)
                    ExifInterface.ORIENTATION_ROTATE_270, ExifInterface.ORIENTATION_TRANSVERSE -> image =
                        TransformationUtils.rotateImage(image, 270)
                    else -> {
                    }
                }
                inputstream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return image
        }
        fun scaleBitmap(input: Bitmap, maxBytes: Long): Bitmap? {
            val currentWidth = input.width
            val currentHeight = input.height
            val currentPixels = currentWidth * currentHeight
            // Get the amount of max pixels:
            // 1 pixel = 4 bytes (R, G, B, A)
            val maxPixels = maxBytes / 4 // Floored
            if (currentPixels <= maxPixels) {
                // Already correct size:
                return input
            }
            // Scaling factor when maintaining aspect ratio is the square root since x and y have a relation:
            val scaleFactor = Math.sqrt(maxPixels / currentPixels.toDouble())
            val newWidthPx = Math.floor(currentWidth * scaleFactor).toInt()
            val newHeightPx = Math.floor(currentHeight * scaleFactor).toInt()
            Log.d("BMSize",
                "Scaled bitmap sizes are ${newWidthPx} x ${newHeightPx} when original sizes are ${currentWidth} x ${currentHeight} and currentPixels ${currentPixels} and maxPixels ${maxPixels} and scaled total pixels are: ${newWidthPx * newHeightPx}",)
            return Bitmap.createScaledBitmap(input, newWidthPx, newHeightPx, true)
        }

    }


}