package com.example.tetrisandroid.pieces

open class PieceShard(var x: Int, var y: Int) {
    fun moveDown() { x++ }

    fun moveRight() { y++ }

    fun moveLeft() { y-- }
}