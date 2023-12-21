package com.capstone.terratani.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityResultCropBinding
import com.capstone.terratani.ui.main.MainActivity

class ResultCropActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultCropBinding
    lateinit var btnSelesai: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnSelesai = binding.btnDone
        btnSelesai.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}