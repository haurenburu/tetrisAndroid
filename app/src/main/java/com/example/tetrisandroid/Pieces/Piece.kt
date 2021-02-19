package com.example.tetrisandroid.Pieces

abstract class Piece(var x: Int, var y: Int){
    abstract var shard1: PieceShard
    abstract var shard2: PieceShard
    abstract var shard3: PieceShard
    abstract var shard4: PieceShard

    open fun flip() {
        // TODO: vem que tem
    }


    open fun moveDown() {
        shard1.moveDown()
        shard2.moveDown()
        shard3.moveDown()
        shard4.moveDown()
    }
    open fun moveRight(){
        shard1.moveRight()
        shard2.moveRight()
        shard3.moveRight()
        shard4.moveRight()
    }
    open fun moveLeft(){
        shard1.moveLeft()
        shard2.moveLeft()
        shard3.moveLeft()
        shard4.moveLeft()
    }
}