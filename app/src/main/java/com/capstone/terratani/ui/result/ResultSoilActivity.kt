package com.capstone.terratani.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.terratani.databinding.ActivitySoilResultBinding
import com.capstone.terratani.ui.main.MainActivity

class ResultSoilActivity : AppCompatActivity() {
    lateinit var binding: ActivitySoilResultBinding

    lateinit var btnDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnDone = binding.btnDone
        btnDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}