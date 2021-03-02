package com.example.tetrisandroid.pieces

class PieceI (x: Int,y: Int) : Piece(x, y){
    override var shard1 = PieceShard(x,y-3)
    override var shard3 = PieceShard(x,y-2)
    override var shard4 = PieceShard(x,y-1)
    override var shard2 = PieceShard(x,y)
    var state = 1
    override fun flip() {
        super.flip()

        if(state ==1) {
            shard1.x = shard2.x -1
            shard1.y = shard2.y

            shard3.x = shard2.x +1
            shard3.y = shard2.y

            shard4.x = shard2.x + 2
            shard4.y = shard2.y

            state++
        } else if(state ==1) {
            shard1.x = shard2.x
            shard1.y = shard2.y -1

            shard3.x = shard2.x
            shard3.y = shard2.y+1

            shard4.x = shard2.x
            shard4.y = shard2.y +2

            state = 1
        }

    }
}