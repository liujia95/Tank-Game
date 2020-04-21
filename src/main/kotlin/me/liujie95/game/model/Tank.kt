package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.enums.Direction
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) :View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    var currentDirection:Direction = Direction.UP
    val speed = 60

    override fun draw() {
        var imagePath = when(currentDirection){
            Direction.UP->"/img/tank/tankU.gif"
            Direction.DOWN-> "/img/tank/tankD.gif"
            Direction.LEFT-> "/img/tank/tankL.gif"
            Direction.RIGHT->"/img/tank/tankR.gif"
        }
        Painter.drawImage(imagePath,x,y)
    }

    fun move(direction: Direction){
        if(currentDirection!=direction){
            currentDirection = direction;
            return
        }
        when(currentDirection){
            Direction.UP -> y-=speed
            Direction.DOWN -> y+=speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT -> x+=speed
        }
    }

}