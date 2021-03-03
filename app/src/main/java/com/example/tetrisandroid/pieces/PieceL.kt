package com.example.tetrisandroid.pieces

class PieceL (x: Int,y: Int) : Piece(x, y){
    override var shard1 = PieceShard(x+1,y-2)
    override var shard2 = PieceShard(x,y)
    override var shard3 = PieceShard(x+1,y-1)
    override var shard4 = PieceShard(x+1,y)
    var state = 1
    override fun flip() {
        super.flip()

        when (state) {
            1 -> {
                shard1.x = shard3.x - 1
                shard1.y = shard3.y

                shard2.x = shard3.x + 1
                shard2.y = shard3.y

                shard4.x = shard3.x + 1
                shard4.y = shard3.y + 1

                state++
            }
            2 -> {
                shard1.x = shard3.x
                shard1.y = shard3.y - 1

                shard2.x = shard3.x + 1
                shard2.y = shard3.y - 1

                shard4.x = shard3.x
                shard4.y = shard3.y + 1

                state++

            }
            3 -> {
                shard1.x = shard3.x
                shard1.y = shard3.y - 1

                shard2.x = shard3.x + 1
                shard2.y = shard3.y

                shard4.x = shard3.x + 2
                shard4.y = shard3.y

                state++
            }
            else -> {
                shard1.x = shard3.x
                shard1.y = shard3.y -1

                shard2.x = shard3.x -1
                shard2.y = shard3.y +1

                shard4.x = shard3.x
                shard4.y = shard3.y +1
                state = 1
            }
        }

    }
}