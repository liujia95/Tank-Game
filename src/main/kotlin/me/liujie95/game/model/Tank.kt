package me.liujie95.game.model

import me.liujie95.game.Config
import me.liujie95.game.business.Blockable
import me.liujie95.game.business.Movable
import me.liujie95.game.enums.Direction
import org.itheima.kotlin.game.core.Painter
import java.awt.Rectangle
import java.awt.geom.Rectangle2D

class Tank(override var x: Int, override var y: Int) : Movable {
    override val width: Int = Config.block
    override val height: Int = Config.block

    override var currentDirection:Direction = Direction.UP

    override val speed = 8

    var badDirection:Direction? = null

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

        if(direction == badDirection){
            return
        }

        when(currentDirection){
            Direction.UP -> y-=speed
            Direction.DOWN -> y+=speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT ->x+=speed
        }

        //检测地图边界
        if(x<0) x = 0
        if(x>Config.gameWidth-width) x = Config.gameWidth-width
        if(y<0) y = 0
        if(y>Config.gameHeight-height) y = Config.gameHeight-height
    }

    //检测碰撞
    override fun willCollision(block: Blockable): Direction? {
        var x = this.x
        var y = this.y
        //将要碰撞时去做判断
        when(currentDirection){
            Direction.UP -> y-=speed
            Direction.DOWN -> y+=speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT ->x+=speed
        }
        //检测碰撞，下一步是否碰撞
//        val collision = when {
//            block.y+block.height<=y -> false
//            y+height<=block.y -> false
//            block.x+block.width<=x -> false
//            x+width>block.x -> false
//            else -> true
//        }
        val collision = checkCollision(block.x,block.y,block.width,block.height,x,y,width,height)
        return if(collision)currentDirection else null
    }

    override fun notifyDirection(direction: Direction?) {
        badDirection = direction
    }

    fun shot(): Bullet {
        return Bullet(currentDirection,{bulletWidth,bulletHeight->
            var bulletX = 0
            var bulletY = 0
            when(currentDirection){
                Direction.UP->{
                    bulletX = this.x + (width-bulletWidth)/2
                    bulletY = this.y - bulletHeight/2
                }
                Direction.LEFT->{
                    bulletX = this.x - bulletWidth/2
                    bulletY = this.y + (height-bulletHeight)/2
                }
                Direction.RIGHT->{
                    bulletX = this.x + width+bulletWidth/2
                    bulletY = this.y + (height-bulletHeight)/2
                }
                Direction.DOWN->{
                    bulletX = this.x + (width-bulletWidth)/2
                    bulletY = this.y + height+bulletHeight/2
                }
            }
            Pair(bulletX,bulletY)
        })
    }
}