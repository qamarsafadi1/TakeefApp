package com.selsela.takeefapp.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface.ORIENTATION_NORMAL
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.Source
import okio.source
import java.io.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


open class FileHandler {
    companion object {
        fun createFile(context: Context, it: Uri, filename: String): File {
            val path = getPath(context)
            val filePath = File(path)
            filePath.mkdirs()
            val outFile = File("${path + filename}.mp4")
            context.contentResolver?.openInputStream(it)?.let { it1 ->
                copyStreamToFile(it1, outFile)
            }
            return outFile

        }

        fun getPath(context: Context): String {
            return context.getExternalFilesDir(null)?.absolutePath + File.separator + "Tasfiah"
        }

        fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
            try {
                inputStream.use { input ->
                    val outputStream = FileOutputStream(outputFile)
                    outputStream.use { output ->
                        val buffer = ByteArray(4 * 1024) // buffer size
                        while (true) {
                            val byteCount = input.read(buffer)
                            if (byteCount < 0) break
                            output.write(buffer, 0, byteCount)
                        }
                        output.flush()
                    }
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }

        }

        fun createImageFile(image: File?, key: String): MultipartBody.Part {
            val requestBody = image?.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(key, image?.name ?: "123", requestBody!!)
        }

        fun createFile(image: File?, key: String): MultipartBody.Part {
            val requestBody = image?.asRequestBody("application/pdf".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(key, image?.name ?: "123", requestBody!!)
        }

        fun createPartFromString(descriptionString: String?): RequestBody {
            return descriptionString?.toRequestBody(MultipartBody.FORM) ?: "".toRequestBody(
                MultipartBody.FORM
            )
        }

        fun convertTotMultiPart(image: File?, key: String): MultipartBody.Part? {
            val requestBody = image?.asRequestBody("image/*".toMediaTypeOrNull())
            return requestBody?.let {
                MultipartBody.Part.createFormData(
                    key, image.name ?: "123",
                    it
                )
            }

        }

        fun convertFileTotMultiPart(
            file: RequestBody,
            key: String,
            name: String
        ): MultipartBody.Part {

            return file.let {
                MultipartBody.Part.createFormData(
                    key, name,
                    it
                )
            }
        }

        fun convertFileTotMultiPart2(file: RequestBody?, key: String): MultipartBody.Part? {
            return file?.let {
                MultipartBody.Part.create(
                    it
                )
            }
        }

        private fun convertUriListToFileList(
            context: Context,
            filesUris: ArrayList<Uri>
        ): ArrayList<File> {
            val files: ArrayList<File> = arrayListOf()
            for (item in filesUris) {
                getImage(item, context)?.let { files.add(it) }
            }
            return files
        }

        fun getVideo(context: Context): File {
            val timeStamp = Timestamp(System.currentTimeMillis()).time
            val fileName = "video${timeStamp}.mkv"
            val folder: File = if (Build.VERSION.SDK_INT >= 29) {
                File("${context.filesDir?.path!!}/jobs/${fileName}")
            } else File("${Environment.getExternalStorageDirectory()}${File.separator}/jobs/${fileName}")
            return folder
        }

        fun getImage(uri: Uri?, context: Context): File? {
            val bitmapImage = compressImage(context, uri)
            val cw = ContextWrapper(context)
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "JPEG_$timeStamp.jpg"
            val mypath = File(directory, imageFileName)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage?.compress(Bitmap.CompressFormat.PNG, 100, fos)
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

        fun compressImage(context: Context, selectedImage: Uri?): Bitmap? {
            var bm: Bitmap? = null
            val sampleSizes = intArrayOf(5, 3, 2, 1)
            var i = 0
            do {
                bm = decodeBitmap(context, selectedImage, sampleSizes[i])
                Log.d("TAG", "resizer: new bitmap width = " + bm!!.width)
                i++
            } while (bm!!.width < 400 && i < sampleSizes.size)
            return bm
        }

        fun decodeBitmap(
            context: Context, theUri: Uri?, sampleSize: Int
        ): Bitmap? {
            val exif: ExifInterface?

            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize
            var fileDescriptor: AssetFileDescriptor? = null
            try {
                fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri!!, "r")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            var actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                fileDescriptor!!.fileDescriptor, null, options
            )
            var orientation: Int = ExifInterface.ORIENTATION_UNDEFINED
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    exif = fileDescriptor.createInputStream()?.let { ExifInterface(it) }
                } else {
                    exif = theUri?.path?.let { ExifInterface(it) }
                }
                orientation = exif?.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                ) ?: ORIENTATION_NORMAL
            } catch (e: IOException) {
                // Log.d("IOException: " + e.message)
            }

            //if you need, can correct orientation issues for gallery pick camera images with following.
            //Log.d("decodeBitmap orientation: $orientation")
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90, ExifInterface.ORIENTATION_TRANSPOSE -> actuallyUsableBitmap =
                    rotateImage(actuallyUsableBitmap, 90)
                ExifInterface.ORIENTATION_ROTATE_180, ExifInterface.ORIENTATION_FLIP_VERTICAL -> actuallyUsableBitmap =
                    rotateImage(actuallyUsableBitmap, 180)
                ExifInterface.ORIENTATION_ROTATE_270, ExifInterface.ORIENTATION_TRANSVERSE -> actuallyUsableBitmap =
                    rotateImage(actuallyUsableBitmap, 270)
                else -> {
                }
            }
            fileDescriptor.close()
            Log.d(
                "TAG",
                options.inSampleSize.toString() + " sample method bitmap ... " + actuallyUsableBitmap.width + " " + actuallyUsableBitmap.height
            )
            return actuallyUsableBitmap
        }


        fun decodeBitmap(
            context: Context, imagePath: Uri
        ): Bitmap? {


            var inputstream: InputStream?
            val exif: ExifInterface?
            var actuallyUsableBitmap: Bitmap? = null
            try {
                val options = BitmapFactory.Options()
                options.inSampleSize = 2
                var fileDescriptor: AssetFileDescriptor? = null
                try {
                    fileDescriptor =
                        context.contentResolver.openAssetFileDescriptor(imagePath!!, "r")
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                    fileDescriptor!!.fileDescriptor, null, options
                )
                inputstream = context.contentResolver.openInputStream(imagePath)


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
                        exif = imagePath.path?.let { ExifInterface(it) }
                    }
                    orientation = exif?.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                    ) ?: ORIENTATION_NORMAL
                } catch (e: IOException) {
                    // Log.d("IOException: " + e.message)
                }

                //if you need, can correct orientation issues for gallery pick camera images with following.
                //Log.d("decodeBitmap orientation: $orientation")
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90, ExifInterface.ORIENTATION_TRANSPOSE -> actuallyUsableBitmap =
                        rotateImage(actuallyUsableBitmap, 90)
                    ExifInterface.ORIENTATION_ROTATE_180, ExifInterface.ORIENTATION_FLIP_VERTICAL -> actuallyUsableBitmap =
                        rotateImage(actuallyUsableBitmap, 180)
                    ExifInterface.ORIENTATION_ROTATE_270, ExifInterface.ORIENTATION_TRANSVERSE -> actuallyUsableBitmap =
                        rotateImage(actuallyUsableBitmap, 270)
                    else -> {
                    }
                }
                inputstream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return actuallyUsableBitmap
        }

        fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                inContext.contentResolver,
                inImage,
                "",
                null
            )
            return Uri.parse(path)
        }

        fun encodeToBase64(bm: Bitmap): String? {

            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.NO_WRAP)
        }


        fun create(mediaType: MediaType?, inputStream: InputStream): RequestBody {
            return object : RequestBody() {
                override fun contentType(): MediaType? {
                    return mediaType
                }

                override fun contentLength(): Long {
                    return try {
                        inputStream.available().toLong()
                    } catch (e: IOException) {
                        0
                    }
                }

                @Throws(IOException::class)
                override fun writeTo(sink: BufferedSink) {
                    var source: Source? = null
                    try {
                        source = inputStream.source()
                        sink.writeAll(source)
                    } catch (ex: java.lang.Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }


        private val IMAGE_DIRECTORY = "/jobs_upload_gallery"

        open fun getFilePathFromURI(context: Context?, contentUri: Uri?): String? {
            //copy file and send new file path
            val wallpaperDirectory = File(
                "${ Environment.getExternalStorageDirectory()}${IMAGE_DIRECTORY}"
            )
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs()
            }
            val copyFile = File(
                "${wallpaperDirectory}${File.separator + Calendar.getInstance().timeInMillis.toString()}"+
                        ".mp4"
            )
            // create folder if not exists
            copy(context!!, contentUri, copyFile)
            Log.d("vPath--->", copyFile.absolutePath)
            return copyFile.absolutePath
        }

        open fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
            try {
                val inputStream = context.contentResolver.openInputStream(srcUri!!) ?: return
                val outputStream: OutputStream = FileOutputStream(dstFile)
                copystream(inputStream, outputStream)
                inputStream.close()
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        private val BUFFER_SIZE = 1024 * 2

        @Throws(java.lang.Exception::class, IOException::class)
        open fun copystream(input: InputStream?, output: OutputStream?): Int {
            val buffer = ByteArray(BUFFER_SIZE)
            val `in` = BufferedInputStream(input, BUFFER_SIZE)
            val out = BufferedOutputStream(output, BUFFER_SIZE)
            var count = 0
            var n = 0
            try {
                while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
                    out.write(buffer, 0, n)
                    count += n
                }
                out.flush()
            } finally {
                try {
                    out.close()
                } catch (e: IOException) {
                    Log.e(e.message, java.lang.String.valueOf(e))
                }
                try {
                    `in`.close()
                } catch (e: IOException) {
                    Log.e(e.message, java.lang.String.valueOf(e))
                }
            }
            return count
        }

        open fun createThumbnail(activity: Activity?, path: String?): Bitmap? {
            var mediaMetadataRetriever: MediaMetadataRetriever? = null
            var bitmap: Bitmap? = null
            try {
                mediaMetadataRetriever = MediaMetadataRetriever()
                mediaMetadataRetriever.setDataSource(activity, Uri.parse(path))
                bitmap = mediaMetadataRetriever.getFrameAtTime(
                    1000,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                mediaMetadataRetriever?.release()
            }
            return bitmap
        }

        fun getThumbVideo(context: Context?, videoUri: Uri?): Bitmap? {
            var bitmap: Bitmap? = null
            var mediaMetadataRetriever: MediaMetadataRetriever? = null
            try {
                mediaMetadataRetriever = MediaMetadataRetriever()
                mediaMetadataRetriever.setDataSource(context, videoUri)
                bitmap = mediaMetadataRetriever.getFrameAtTime(
                    1000,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                )
              //  bitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 343, 185, false) };

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                mediaMetadataRetriever?.release()
            }
            return bitmap
        }
    }

}