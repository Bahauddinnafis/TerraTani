package com.capstone.terratani.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.terratani.R
import com.capstone.terratani.databinding.ActivityResultYieldBinding
import com.capstone.terratani.ui.main.MainActivity

class ResultYieldActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultYieldBinding
    lateinit var btnDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultYieldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnDone = binding.btnDone
        btnDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}