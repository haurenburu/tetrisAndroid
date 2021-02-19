package com.example.tetrisandroid.Pieces

open class PieceShard(var x: Int, var y: Int) {
    fun moveDown() { y++ }

    fun moveRight() { x++ }

    fun moveLeft() { x-- }
}