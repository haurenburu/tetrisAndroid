package com.example.tetrisandroid.pieces

class PieceT (x: Int,y: Int) : Piece(x, y){
    override var shard1 = PieceShard(x,y)
    override var shard2 = PieceShard(x,y-1)
    override var shard3 = PieceShard(x,y+1)
    override var shard4 = PieceShard(x+1,y)
}