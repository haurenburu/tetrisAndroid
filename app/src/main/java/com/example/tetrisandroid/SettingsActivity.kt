package com.example.tetrisandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tetrisandroid.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        val settings = getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = settings.edit()
        val dif = settings.getString("difficulty", "normal");

        when (dif) {
            "easy" -> {
                binding.radioButtonEasy.isChecked = true;
            }
            "hard" -> {
                binding.radioButtonHard.isChecked = true;
            }
            else -> {
                binding.radioButtonNormal.isChecked = true;
            }
        }


        binding.buttonSave.setOnClickListener {
            when {
                binding.radioButtonEasy.isChecked -> editor.putString("difficulty", "easy")
                binding.radioButtonNormal .isChecked -> editor.putString("difficulty", "normal")
                binding.radioButtonHard.isChecked -> editor.putString("difficulty", "hard")
            }

            editor.apply();
        }
    }
}