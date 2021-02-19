package com.example.tetrisandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.tetrisandroid.Pieces.PieceShard
import com.example.tetrisandroid.databinding.ActivityGameBinding
import com.example.tetrisandroid.databinding.ActivityMainBinding
import java.lang.reflect.Array

class GameActivity : AppCompatActivity() {
    val LINE = 36
    val COL = 26
    var running = true
    var speed: Long = 300

    var piece = PieceShard(15, 15)

    var board = Array(LINE) {
        Array(COL){0}
    }

    var boardView = Array(LINE) {
        arrayOfNulls<ImageView>(COL)
    }

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        binding.gameGrid.rowCount = LINE
        binding.gameGrid.columnCount = COL

        val inflater = LayoutInflater.from(this)

        for(i in 0 until LINE) {
            for(j in 0 until COL) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, binding.gameGrid, false) as ImageView
                binding.gameGrid.addView(boardView[i][j])
            }
        }

        gameRun()
    }

    fun gameRun() {
        Thread {
            while (running) {
                Thread.sleep(speed)
                runOnUiThread {
                    for (i in 0 until LINE) {
                        for (j in 0 until COL) {
                            boardView[i][j]!!.setImageResource(R.drawable.black)
                        }
                    }

                    piece.moveDown()
                    try {
                        boardView[piece.x][piece.y]!!.setImageResource(R.drawable.white)
                    } catch (e:ArrayIndexOutOfBoundsException) {
                        running = false
                    }
                }
            }
        }.start()
    }
}