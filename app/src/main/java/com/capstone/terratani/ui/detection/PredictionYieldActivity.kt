package com.capstone.terratani.ui.detection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityPredictionYieldBinding
import com.capstone.terratani.ui.result.ResultYieldActivity

class PredictionYieldActivity : AppCompatActivity() {
    lateinit var binding: ActivityPredictionYieldBinding
    lateinit var btnResult: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionYieldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnResult = binding.btnPrediction
        btnResult.setOnClickListener {
            val intent = Intent(this, ResultYieldActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}