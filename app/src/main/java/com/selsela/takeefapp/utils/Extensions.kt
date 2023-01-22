package com.selsela.takeefapp.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.format.DateUtils
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.muddzdev.styleabletoast.StyleableToast
import com.selsela.takeefapp.R
import com.selsela.takeefapp.navigation.Destinations
import com.selsela.takeefapp.utils.FileHelper.Companion.absoulutePath
import com.selsela.takeefapp.utils.FileHelper.Companion.compressImage
import com.selsela.takeefapp.utils.FileHelper.Companion.scaleBitmap
import com.selsela.takeefapp.utils.retrofit.model.ErrorBase
import com.selsela.takeefapp.utils.retrofit.model.Resource
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class Extensions {
    companion object {
        fun <T> (() -> T).withDelay(delay: Long = 250L) {
            Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
        }

        fun Context.findDrawable(color: Int): Int {
            return ContextCompat.getColor(this, color)
        }

        fun Int.convertToDecimalPatter(): String {
            return DecimalFormat(
                "00",
                DecimalFormatSymbols(Locale.US)
            ).format(this)
        }

        fun Any.log(tag: String = "") {
            if (tag.equals("")) {
                Log.d("QMR : ", this.toString())
            } else {
                Log.d("QMR$tag", this.toString())

            }
        }

        @Composable
        fun Context.RequestPermission(
            permission: String,
            onGranted: (Boolean) -> Unit
        ) {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                onGranted(isGranted)
            }
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) -> {
                    onGranted(true)
                }

                else -> {
                    SideEffect { // SideEffect just when you need to request your permission
                        // first time before composition
                        launcher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    }
                }
            }


        }

        fun getMyLocation(
            context: Context,
            onMyLocation: (LatLng) -> Unit
        ) {
            try {
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val currentLocation = LatLng(location.latitude, location.longitude)
                        onMyLocation(currentLocation)
                    } ?: run {}
                }

            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        fun bitmapDescriptor(
            context: Context,
            vectorResId: Int
        ): BitmapDescriptor? {

            // retrieve the actual drawable
            val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            // draw it onto the bitmap
            val canvas = android.graphics.Canvas(bm)
            drawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bm)
        }

        fun <T> Context.navigate(
            fromActivity: Context, toActivity: Class<T>, isLogin: Boolean? = true
        ) {
            val navIntent = Intent(fromActivity, toActivity)
            navIntent.putExtra("isLogin", isLogin)
            this.startActivity(navIntent)
            this.getActivity()?.finish()
        }

        fun Context.getActivity(): AppCompatActivity? = when (this) {
            is AppCompatActivity -> this
            is ContextWrapper -> baseContext.getActivity()
            else -> null
        }

        fun <E> handleSuccess(data: E?, message: String? = ""): MutableStateFlow<Resource<E>> {
            return MutableStateFlow(
                Resource.success(
                    data,
                    message = message
                )
            )
        }


        fun <E> handleExceptions(errorBase: Exception): MutableStateFlow<Resource<E>> {
            return MutableStateFlow(
                Resource.error(
                    null,
                    errorBase.message,
                    null
                )
            )
        }

        fun <E> handleExceptions(errorBase: ErrorBase): MutableStateFlow<Resource<E>> {
            return MutableStateFlow<Resource<E>>(
                Resource.error(
                    null,
                    errorBase.responseMessage,
                    errorBase.errors
                )
            )
        }


        fun Activity.showSuccessTop(message: String) {
            Alerter.create(this)
                .setTitle("")
                .setText(message)
                .setTitleAppearance(R.style.AlertTextAppearance_Title_1)
                .setTextAppearance(R.style.AlertTextAppearance_Text_1)
                .setBackgroundColorInt(Color.parseColor("#61BF61")) // or setBackgroundColorInt(Color.CYAN)
                .setIcon(R.drawable.component_6___2)
                .setIconColorFilter(0) //
                .show()
        }


        fun Context.showSuccess(message: String) {
            StyleableToast.Builder(this)
                .text(message)
                .textColor(Color.WHITE)
                .backgroundColor(
                    Color.parseColor("#61BF61")
                )
                .cornerRadius(5)
                .font(R.font.sf_book)
                .gravity(Gravity.TOP)
                .iconStart(R.drawable.component_6___2)
                .length(Toast.LENGTH_LONG)
                .solidBackground()
                .stroke(
                    1, Color.parseColor("#61BF61")
                )
                .show()
        }

        fun Context.showError(message: String) {
            StyleableToast.Builder(this)
                .text(message)
                .textColor(Color.WHITE)
                .backgroundColor(
                    Color.parseColor("#E54342")
                )
                .cornerRadius(5)
                .font(R.font.sf_book)
                .gravity(Gravity.TOP)
                .iconStart(R.drawable.svgexport_10__10_)
                .length(Toast.LENGTH_LONG)
                .solidBackground()
                .stroke(
                    1, Color.parseColor("#E54342")
                )
                .show()
        }


        @Composable
        fun <T> rememberFlow(
            flow: Flow<T>,
            lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
        ): Flow<T> {
            return remember(key1 = flow, key2 = lifecycleOwner) {
                flow.flowWithLifecycle(
                    lifecycleOwner.lifecycle,
                    Lifecycle.State.STARTED
                )
            }
        }

        @Composable
        fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
            initial: R,
            context: CoroutineContext = EmptyCoroutineContext
        ): State<R> {
            val lifecycleAwareFlow = rememberFlow(flow = this)
            return lifecycleAwareFlow.collectAsState(initial = initial, context = context)
        }

        fun Context.showAlertDialog(
            title: String, content: String,
            positiveButtonText: String,
            negativeButtonText: String,
            onPositiveClick: () -> Unit
        ) {
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonText) { dialog, _ ->
                    dialog.dismiss()
                    onPositiveClick()
                }.setNegativeButton(negativeButtonText) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }

        fun getCurrency(): Any {
            return (if (LocalData.appLocal == "ar")
                LocalData.configurations?.currencyAr
            else LocalData.configurations?.currencyEn)!!
        }

        @Composable
        fun mStartActivityForResult(
            context: Context,
            lambda: (File?, Bitmap?) -> Unit
        ): ManagedActivityResultLauncher<Intent, ActivityResult> {
            return rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = { result ->
                    val data = result.data
                    if (result.resultCode == Activity.RESULT_OK) {
                        try {
                            if (data != null && data.data != null) {
                                val image = FileHelper.getImage(data.data, context)
                                val bitmap = FileHelper.decodeBitmap(context, data.data!!)
                                lambda(image, bitmap)

                                /*   if (bitmap != null && Common.validImageSize(
                                           FileUtils.getFile(
                                               requireContext(),
                                               data.data!!
                                           )
                                       )
                                   ) {
                                       lambda(image, bitmap)
                                       *//*  viewModel.file = image
                       binding?.imageIv?.setImageBitmap(bitmap)*//*
                        //   viewModel.bitmaps.add(bitmap)
                    } else {
                        context?.showError(getString(R.string.msg_size_exced_max))

                    }*/
                            } else if (data != null && data.clipData != null) {

                                val mClipData: ClipData? = data.clipData
                                var imageValidSize = true

                                if (mClipData != null)
                                    for (i in 0 until mClipData.itemCount) {
                                        val item: ClipData.Item = mClipData.getItemAt(i)
                                        val uri: Uri = item.uri
                                        val image = FileHelper.getImage(uri, context)
                                        val bitmap = FileHelper.decodeBitmap(context, uri)
                                        lambda(image, bitmap)

                                        /*if (bitmap != null && Common.validImageSize(bitmap)) {
                                            lambda(image,bitmap)

                                        } else {
                                            imageValidSize = false
                                        }*/
                                    }
                                /*                    if (imageValidSize.not()) {
                                                        context?.showError(getString(R.string.msg_size_exced_max))

                                                    }*/

                            } else if (absoulutePath != null) {
                                BitmapFactory.Options().apply {
                                    // Get the dimensions of the bitmap
                                    inJustDecodeBounds = true
                                    var bitmap = compressImage(
                                        context!!,
                                        Uri.fromFile(File(absoulutePath!!))
                                    )
                                    bitmap = scaleBitmap(bitmap!!, 2000*1000)
                                    val image = FileHelper.getBitmapImage(
                                        bitmap!!,
                                        context
                                    )
                                    lambda(image, bitmap)

                                    if (Common.validImageSize(bitmap)) {
                                        lambda(image, bitmap)
                                    } else {
                                        context.showError(context.getString(R.string.max_att_size))
                                    }

                                }
                            }


                            /* else if (data.extras?.get("data") != null) {
                                    val image = FileHelper.getBitmapImage(
                                        mActivity,
                                        data.extras?.get("data")!! as Bitmap,
                                        requireContext()
                                    )
                                    val bitmap = data.extras?.get("data")!! as Bitmap
                                    if (Common.validImageSize(bitmap)) {
                                        lambda(image, bitmap)
                                        //   viewModel.bitmaps.add(bitmap)
                                    } else {
                                        context?.showError(getString(R.string.msg_size_exced_max))

                                    }
                                } */
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                })
        }

        fun uploadImages(
            context: Context,
            images: ActivityResultLauncher<Intent>,
            isMultiple: Boolean
        ) {
//    permissionsBuilder(
//        Manifest.permission.READ_EXTERNAL_STORAGE
//    ).build().send {
            kotlin.run {
                // if (it.allGranted()) {
                absoulutePath = null

                try {
                    val cameraIntent =
                        Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )

                    cameraIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMultiple);
                    val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    // takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getFileDirectory())
                    takePhotoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1)
                    // Create the File where the photo should go

                    val photoFile: File =
                        context.createFullImageFile()

                    // Continue only if the File was successfully created
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context,
                        "com.selsela.takeefapp.fileprovider",
                        photoFile
                    )

                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    val pickTitle = "Select or take a new Picture"
                    val chooserIntent = Intent.createChooser(cameraIntent, pickTitle)
                    chooserIntent.putExtra(
                        Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePhotoIntent)
                    )
                    images.launch(chooserIntent)


                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
            //   }
            //  }
        }
        fun Context.createFullImageFile(): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val ff = File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
            absoulutePath = ff.absolutePath

            return ff
        }


        fun String.getTimeAgo(): String {
            var text = ""
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
            val date: Date = sdf.parse(this)
            val time = DateUtils.getRelativeTimeSpanString(
                date.time,
                Date().time, DateUtils.MINUTE_IN_MILLIS
            )
            text = if (time == "قبل ٠ دقيقة")
                "الآن"
            else time.toString().preventArabicNumber()
            return text
        }

        fun String.preventArabicNumber(): String {
            val input = this
            if (input.isEmpty()) {
                return input
            }
            val builder = StringBuilder()
            for (element in input) {
                val ch = element
                if (Character.isDigit(ch) && !(ch in '0'..'9')) {
                    val numericValue = Character.getNumericValue(ch)
                    if (numericValue >= 0) {
                        builder.append(numericValue)
                    }
                } else {
                    builder.append(ch)
                }
            }
            return builder.toString()
        }

    }
}