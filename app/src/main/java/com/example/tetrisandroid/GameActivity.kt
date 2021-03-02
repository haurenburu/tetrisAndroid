package com.example.tetrisandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tetrisandroid.databinding.ActivityGameBinding
import com.example.tetrisandroid.pieces.*

class GameActivity : AppCompatActivity() {
    private val LINE = 36
    private val COL = 6
    private var running = true
    private var speed: Long = 300

    lateinit var binding: ActivityGameBinding
    lateinit var viewmodel: GameViewModel

    var piece: Piece = randPiece()

    var boardView = Array(LINE) {
        arrayOfNulls<ImageView>(COL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settings = getSharedPreferences("prefs", MODE_PRIVATE)
        when (settings.getString("difficulty", "normal")) {
            "easy" -> speed = 500
            "normal" -> speed = 300
            "hard" -> speed = 100
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        viewmodel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameGrid.rowCount = LINE
        binding.gameGrid.columnCount = COL

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINE) {
            for (j in 0 until COL) {
                boardView[i][j] = inflater.inflate(
                    R.layout.inflate_image_view,
                    binding.gameGrid,
                    false
                ) as ImageView
                binding.gameGrid.addView(boardView[i][j])
            }
        }

        binding.ButtonPause.setOnClickListener {
            if (running) {
                running = false
            } else {
                running = true;
                gameRun()
            }
        }

        binding.ButtonLeft.setOnClickListener {
            if (!running ||
                piece.shard1.y == 0 ||
                piece.shard2.y == 0 ||
                piece.shard3.y == 0 ||
                piece.shard4.y == 0 ||
                viewmodel.board[piece.shard1.x][piece.shard1.y - 1] == 1 ||
                viewmodel.board[piece.shard2.x][piece.shard2.y - 1] == 1 ||
                viewmodel.board[piece.shard3.x][piece.shard3.y - 1] == 1 ||
                viewmodel.board[piece.shard4.x][piece.shard4.y - 1] == 1
            ) {
                return@setOnClickListener
            }

            piece.moveLeft()
        }

        binding.ButtonFlip.setOnClickListener {
            piece.flip()
        }

        binding.ButtonRight.setOnClickListener {
            if (!running ||
                piece.shard1.y + 1 == COL ||
                piece.shard2.y + 1 == COL ||
                piece.shard3.y + 1 == COL ||
                piece.shard4.y + 1 == COL ||
                viewmodel.board[piece.shard1.x][piece.shard1.y + 1] == 1 ||
                viewmodel.board[piece.shard2.x][piece.shard2.y + 1] == 1 ||
                viewmodel.board[piece.shard3.x][piece.shard3.y + 1] == 1 ||
                viewmodel.board[piece.shard4.x][piece.shard4.y + 1] == 1
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
                            if (viewmodel.board[i][j] == 1) {
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            } else {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }

                    if (canMoveDown()) {
                        piece.moveDown()
                    } else {
                        checkPoints()
                    }

                    try {
                        boardView[piece.shard1.x][piece.shard1.y]!!.setImageResource(R.drawable.white)
                        boardView[piece.shard2.x][piece.shard2.y]!!.setImageResource(R.drawable.white)
                        boardView[piece.shard3.x][piece.shard3.y]!!.setImageResource(R.drawable.white)
                        boardView[piece.shard4.x][piece.shard4.y]!!.setImageResource(R.drawable.white)
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        running = false
                    }
                }
            }
        }.start()
    }

    private fun randPiece(): Piece {
        return PieceL(5, 2)
        return when ((1..7).random()) {
            1 -> PieceI(5, 15)
            2 -> PieceJ(5, 15)
            3 -> PieceL(5, 15)
            4 -> PieceO(5, 15)
            5 -> PieceS(5, 15)
            6 -> PieceT(5, 15)
            else -> PieceZ(5, 15)
        }
    }

    override fun onStop() {
        running = false;
        super.onStop()
    }

    private fun checkPoints() {
        viewmodel.board[piece.shard1.x][piece.shard1.y] = 1
        viewmodel.board[piece.shard2.x][piece.shard2.y] = 1
        viewmodel.board[piece.shard3.x][piece.shard3.y] = 1
        viewmodel.board[piece.shard4.x][piece.shard4.y] = 1

        for(i in 0 until LINE) {
            if (viewmodel.board[i].sum() == COL) {
                viewmodel.points += 50
                binding.textViewPointsValue.text = viewmodel.points.toString()

                for (j in i downTo 1) {
                    viewmodel.board[j] = viewmodel.board[j-1]
                }
            }
        }
        piece = randPiece()
    }

    private fun canMoveDown(): Boolean {
        return (
                piece.shard1.x + 1 != LINE &&
                        piece.shard2.x + 1 != LINE &&
                        piece.shard3.x + 1 != LINE &&
                        piece.shard4.x + 1 != LINE &&
                        viewmodel.board[piece.shard1.x + 1][piece.shard1.y] == 0 &&
                        viewmodel.board[piece.shard2.x + 1][piece.shard2.y] == 0 &&
                        viewmodel.board[piece.shard3.x + 1][piece.shard3.y] == 0 &&
                        viewmodel.board[piece.shard4.x + 1][piece.shard4.y] == 0
                )
    }
}