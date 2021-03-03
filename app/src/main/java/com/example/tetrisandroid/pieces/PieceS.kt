package com.example.tetrisandroid.pieces

class PieceS(x: Int,y: Int) : Piece(x, y){
    override var shard1 = PieceShard(x,y)
    override var shard2 = PieceShard(x,y + 1)
    override var shard3 = PieceShard(x+1,y)
    override var shard4 = PieceShard(x+1,y - 1)
    var state = 1
    override fun flip() {
        super.flip()

        if (state == 1) {
            shard1.x = shard3.x
            shard1.y = shard3.y-1

            shard2.x = shard3.x - 1
            shard2.y = shard3.y - 1

            shard4.x = shard3.x + 1
            shard4.y = shard3.y
            state++
        } else if (state == 2){
            shard1.x = shard3.x - 1
            shard1.y = shard3.y

            shard2.x = shard3.x - 1
            shard2.y = shard3.y + 1

            shard4.x = shard3.x
            shard4.y = shard3.y - 1
            state = 1
        }

    }
}