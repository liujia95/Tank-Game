package me.liujie95.game.model

import me.liujie95.game.business.AutoMovable
import me.liujie95.game.enums.Direction
import me.liujie95.game.Config;
import me.liujie95.game.business.AutoShot
import me.liujie95.game.business.Blockable
import me.liujie95.game.business.Movable
import org.itheima.kotlin.game.core.Painter
import java.util.*

/**
 * 敌方坦克
 * 可移动（避开阻挡物）
 * 自己移动（自己动起来）
 */
class Enemy(override var x: Int, override var y: Int) : Movable,AutoMovable,Blockable,AutoShot {

    override var currentDirection: Direction=Direction.DOWN
    override val speed: Int = 8

    override val width: Int = Config.block
    override val height: Int = Config.block

    var badDirection:Direction? = null

    private var lastShotTime = 0L
    private var shotFrequency = 800

    private var lastMoveTime = 0L
    private var moveFrequency = 50

    override fun draw() {
        val imagePath = when(currentDirection){
            Direction.UP->"/img/enemy_1_u.gif"
            Direction.DOWN-> "/img/enemy_1_d.gif"
            Direction.LEFT-> "/img/enemy_1_l.gif"
            Direction.RIGHT->"/img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath,x,y)
    }

    override fun notifyDirection(direction: Direction?, block: Blockable?) {
        badDirection = direction
    }

    override fun autoMove() {
        val currentTime = System.currentTimeMillis()
        if(currentTime - lastMoveTime < moveFrequency) return
        lastMoveTime = currentTime

        if(currentDirection==badDirection){
            //随机改变自己方向
            currentDirection = rdmDirection(badDirection)
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

    private fun rdmDirection(bad:Direction?):Direction{
        val i = Random().nextInt(4)
        val direction = when(i){
            0->Direction.UP
            1->Direction.DOWN
            2->Direction.LEFT
            else -> Direction.RIGHT
        }
        if(direction==bad){
           return rdmDirection(bad)
        }
        return direction
    }

    override fun autoShot(): View? {
        val currentTime = System.currentTimeMillis()
        if(currentTime - lastShotTime < shotFrequency) return null
        lastShotTime = currentTime

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