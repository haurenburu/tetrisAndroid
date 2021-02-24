package com.example.tetrisandroid

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // TODO: config
    val LINE = 36
    val COL = 6

    var points = 0
    var board = Array(LINE) {
        Array(COL){0}
    }
}