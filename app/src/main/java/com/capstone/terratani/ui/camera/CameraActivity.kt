package com.capstone.terratani.ui.camera

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityCameraBinding
import com.capstone.terratani.ui.result.ResultActivity
import java.nio.file.Files.createFile

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
            startCameraX()
        }

        binding.captureImage.setOnClickListener {
            takePhoto()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCameraX()
    }

    override fun onStart() {
        super.onStart()
        eventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        eventListener.disable()
    }

    private fun startCameraX() {
        val cameraProvideFuture = ProcessCameraProvider.getInstance(this)
        cameraProvideFuture.addListener({
            val cameraProvide: ProcessCameraProvider = cameraProvideFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }

            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvide.unbindAll()
                cameraProvide.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Toast.makeText(this@CameraActivity, "Tidak bisa membuka camera", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "start camera ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val imageFile = createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val intent = Intent(this@CameraActivity, ResultActivity::class.java)
                    intent.putExtra(EXTRA_CAMERA_IMAGE, outputFileResults.savedUri.toString())
                    setResult(CAMERA_RESULT, intent)
                    finish()
                }

                override fun onError(e: ImageCaptureException) {
                    Toast.makeText(this@CameraActivity, "Gagal mengambil foto", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error: ${e.message}")
                }
            })
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private val eventListener by lazy { object : OrientationEventListener(this) {
        override fun onOrientationChanged(orientation: Int) {
            if (orientation == ORIENTATION_UNKNOWN) {
                return
            }

            val rotation =
                when(orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }
            imageCapture?.targetRotation = rotation
        }
    }
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val CAMERA_RESULT = 200
        const val EXTRA_CAMERA_IMAGE = "Camera Image"
    }
}