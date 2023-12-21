package com.capstone.terratani.ui.detection

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.capstone.terratani.databinding.ActivityDetectionSoilBinding
import com.capstone.terratani.ui.camera.CameraActivity
import com.capstone.terratani.ui.result.ResultSoilActivity

class DetectionSoilActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetectionSoilBinding

    lateinit var btnResult: Button
    private var currentImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DetectionActivity", "onCreate: Entered DetectionActivity")
        binding = ActivityDetectionSoilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnResult = binding.btnPrediction
        btnResult.setOnClickListener {
            val intent = Intent(this, ResultSoilActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCamera.setOnClickListener {
            startCameraX()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
    }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCameraX()
            } else {
                // Handle the case when the user denies camera permission
                // You might want to show a message or request permission again
            }
        }

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startGallery()
            } else {
                // Handle the case when the user denies gallery permission
                // You might want to show a message or request permission again
            }
        }

    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    private fun requestGalleryPermission() {
        galleryPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == CameraActivity.CAMERA_RESULT) {
            currentImage = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERA_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCamera.launch(intent)
        requestCameraPermission()
    }

    private val lauuncherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val selectedImage: Uri = it.data?.data as Uri
            if (selectedImage != null) {
                currentImage = selectedImage
                showImage()
            }
        }
    }

    private fun showImage() {
        currentImage?.let {
            Log.d("Image Uri", "Show Image: $it}")
            binding.imagePreview.setImageURI(it)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val choose = Intent.createChooser(intent, "Pilih foto")
        lauuncherIntentGallery.launch(choose)
        requestGalleryPermission()
    }
}