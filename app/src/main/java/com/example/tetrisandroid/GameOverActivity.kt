package com.example.tetrisandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tetrisandroid.databinding.ActivityGameOverBinding


class GameOverActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_over)

        var param = intent.extras
        var points = param?.getString("points")

        binding.textViewPointsGOValue.text = points

    }
}