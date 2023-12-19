package com.capstone.terratani.utils

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Location
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import com.capstone.terratani.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILEDATE_FORMAT = "dd-MMM-yyyy"
private val timeStamp: String = SimpleDateFormat(FILEDATE_FORMAT, Locale.US).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory =
        if (mediaDir != null && mediaDir.exists()) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun uriToFile(selectedImage: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)
    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun File.reduceFileImage(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height, matrix, true
    )
}

fun translateWeatherType(type: String): String {
    return when(type) {
        WeatherType.THUNDERSTORM.type -> WeatherType.THUNDERSTORM.translatedType
        WeatherType.DRIZZLE.type -> WeatherType.DRIZZLE.translatedType
        WeatherType.RAIN.type -> WeatherType.RAIN.translatedType
        WeatherType.SNOW.type -> WeatherType.SNOW.translatedType
        WeatherType.CLEAR.type -> WeatherType.CLEAR.translatedType
        WeatherType.CLOUDS.type -> WeatherType.CLOUDS.translatedType
        WeatherType.MIST.type -> WeatherType.MIST.translatedType
        WeatherType.SMOKE.type -> WeatherType.SMOKE.translatedType
        WeatherType.HAZE.type -> WeatherType.HAZE.translatedType
        WeatherType.DUST.type -> WeatherType.DUST.translatedType
        WeatherType.FOG.type -> WeatherType.FOG.translatedType
        WeatherType.SAND.type -> WeatherType.SAND.translatedType
        WeatherType.VOLCANIC_ASH.type -> WeatherType.VOLCANIC_ASH.translatedType
        WeatherType.SQUALL.type -> WeatherType.SQUALL.translatedType
        WeatherType.TORNADO.type -> WeatherType.TORNADO.translatedType
        else -> ""
    }
}

fun createTimeStamp(format: DateFormat): String = run {
    val date = java.util.Date()
    val formatter = SimpleDateFormat(format.format, Locale("id", "ID"))
    formatter.timeZone = java.util.TimeZone.getTimeZone("Asia/Jakarta")
    formatter.format(date)
}

fun determineWeatherIcon(type: String): Int {
    return when(type) {
        WeatherType.THUNDERSTORM.type -> R.drawable.storm
        WeatherType.DRIZZLE.type -> R.drawable.rainy
        WeatherType.RAIN.type -> R.drawable.rainy
        WeatherType.SNOW.type -> R.drawable.snow
        WeatherType.CLEAR.type -> R.drawable.sunny
        WeatherType.CLOUDS.type -> R.drawable.sunny_cloudy
        else -> R.drawable.sunny
    }
}

fun getCurrentLocation(
    context: Context,
    onMyLocation: (LatLng) -> Unit,
) {
    try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
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

fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}
