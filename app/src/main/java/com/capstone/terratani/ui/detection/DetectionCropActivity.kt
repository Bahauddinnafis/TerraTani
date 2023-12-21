package com.capstone.terratani.ui.detection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.terratani.databinding.ActivityDetectionCropBinding
import com.capstone.terratani.ui.result.ResultCropActivity

class DetectionCropActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetectionCropBinding
    lateinit var btnResult: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnResult = binding.btnPrediction
        btnResult.setOnClickListener {
            val intent = Intent(this, ResultCropActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}