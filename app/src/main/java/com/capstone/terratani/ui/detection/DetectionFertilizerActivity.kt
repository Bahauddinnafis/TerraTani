package com.capstone.terratani.ui.detection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityDetectionFertilizerBinding
import com.capstone.terratani.ui.result.ResultFertilizerActivity

class DetectionFertilizerActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetectionFertilizerBinding
    lateinit var btnResult: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionFertilizerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnResult = binding.btnPrediction
        btnResult.setOnClickListener {
            val intent = Intent(this, ResultFertilizerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}