package com.example.tetrisandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tetrisandroid.databinding.ActivityGameBinding
import com.example.tetrisandroid.pieces.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private val LINE = 36
    private val COL = 26
    private var running = true
    private var speed: Long = 300

    var piece: Piece = randPiece()

    var board = Array(LINE) {
        Array(COL){0}
    }

    var boardView = Array(LINE) {
        arrayOfNulls<ImageView>(COL)
    }

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settings = getSharedPreferences("prefs", MODE_PRIVATE)
        val dif = settings.getString("difficulty", "normal")

        Toast.makeText(this, dif, Toast.LENGTH_SHORT).show()

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

        binding.ButtonPause.setOnClickListener {
            if (running){
                running = false
            } else {
                running = true;
                gameRun()
            }

        }

        binding.ButtonLeft.setOnClickListener {
            if (
                piece.shard1.y == 0 ||
                piece.shard2.y == 0 ||
                piece.shard3.y == 0 ||
                piece.shard4.y == 0
            ) {
                return@setOnClickListener
            }

            piece.moveLeft()
        }

        binding.ButtonRight.setOnClickListener {
            if (
                piece.shard1.y + 1 == COL ||
                piece.shard2.y + 1 == COL ||
                piece.shard3.y + 1 == COL ||
                piece.shard4.y + 1 == COL
            ) {
                return@setOnClickListener
            }
            piece.moveRight()
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
                            if (board[i][j] == 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            } else {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }

                    if (
                        piece.shard1.x + 1 != LINE &&
                        piece.shard2.x + 1 != LINE &&
                        piece.shard3.x + 1 != LINE &&
                        piece.shard4.x + 1 != LINE
                    ) {
                        piece.moveDown()
                    } else {
                        board[piece.shard1.x][piece.shard1.y] = 1
                        board[piece.shard2.x][piece.shard2.y] = 1
                        board[piece.shard3.x][piece.shard3.y] = 1
                        board[piece.shard4.x][piece.shard4.y] = 1

                        piece = randPiece()

                    }

                    try {
                        boardView[piece.shard1.x][piece.shard1.y]!!.setImageResource(R.drawable.white)
                        boardView[piece.shard2.x][piece.shard2.y]!!.setImageResource(R.drawable.white)
                        boardView[piece.shard3.x][piece.shard3.y]!!.setImageResource(R.drawable.white)
                        boardView[piece.shard4.x][piece.shard4.y]!!.setImageResource(R.drawable.white)
                    } catch (e:ArrayIndexOutOfBoundsException) {
                        running = false
                    }
                }
            }
        }.start()
    }

    private fun randPiece(): Piece {
        return when((1..7).random()) {
            1 -> PieceI(5,15)
            2 -> PieceJ(5,15)
            3 -> PieceL(5,15)
            4 -> PieceO(5,15)
            5 -> PieceS(5,15)
            6 -> PieceT(5,15)
            else -> PieceZ(5,15)
        }


    }
}